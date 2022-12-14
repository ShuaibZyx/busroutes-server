package com.shuaib.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shuaib.bean.Admin;
import com.shuaib.bean.Files;
import com.shuaib.common.jwt.JwtConfig;
import com.shuaib.bean.UserAccount;
import com.shuaib.bean.UserInfo;
import com.shuaib.common.Result;
import com.shuaib.service.FilesService;
import com.shuaib.service.UserAccountService;
import com.shuaib.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private FilesService filesService;

    @Resource
    private JwtConfig jwtConfig;

    /**
     * 用户注册方法
     *
     * @param userAccount 用户账户对象
     * @return 通用返回格式
     */
    @Transactional
    @PostMapping("/register")
    public Result register(@RequestBody @Validated UserAccount userAccount) {
        userAccountService.save(userAccount);
        //获取创建的用户编号
        Long userId = userAccountService.getOne(new QueryWrapper<UserAccount>().eq("account", userAccount.getAccount())).getUserId();
        //创建附加信息对象并填入数据
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setNickname("自由蝶");
        userInfo.setTelephone(userAccount.getAccount());
        //存入数据库
        userInfoService.save(userInfo);
        return Result.success("注册成功");
    }

    /**
     * 用户登录接口
     *
     * @param loginObject 登录所需要的参数
     * @return 通用返回格式
     */
    @PostMapping("/login")
    public Result login(@RequestBody JSONObject loginObject) {
        String account = loginObject.getStr("account");
        String password = loginObject.getStr("password");
        Long expire = loginObject.getLong("expire");
        JSONObject json = new JSONObject();
        QueryWrapper<UserAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        UserAccount userAccount = userAccountService.getBaseMapper().selectOne(queryWrapper);
        if (userAccount == null) return Result.error("账号不存在,请先注册");
        if (!userAccount.getAvailable()) return Result.error("账号已冻结,如需继续使用请联系管理员");
        if (!password.equals(userAccount.getPassword())) return Result.error("密码错误");
        jwtConfig.setExpire(expire);
        String token = jwtConfig.createToken(userAccount.getUserId().toString());
        String userId = jwtConfig.getUserIdFromToken(token);
        if (!StringUtils.isEmpty(token)) {
            json.set("token", token);
            json.set("userId", userId);
        }
        return Result.success(200, "登陆成功", json);
    }

    /**
     * 分页获取用户信息列表
     *
     * @param currentPage 当前页码
     * @param pageSize    页面大小
     * @return 通用返回格式
     */
    @GetMapping("/list")
    public Result getUserListPage(int currentPage, int pageSize) {
        return Result.success(userAccountService.getUserListPage(currentPage, pageSize));
    }

    /**
     * 获取用户总数
     *
     * @return 通用返回格式
     */
    @GetMapping("/count")
    public Result getUserCount() {
        return Result.success(userAccountService.count());
    }

    /**
     * 根据Id获取用户
     *
     * @param userId 用户编号
     * @return 通用返回格式
     */
    @GetMapping("/info/{userId}")
    public Result getUserInfoById(@PathVariable("userId") Long userId) {
        return Result.success(userInfoService.getUserInfoById(userId));
    }

    /**
     * 根据token获取用户信息
     *
     * @param token token令牌
     * @return 通用返回格式
     */
    @GetMapping("/info/token/{token}")
    public Result getUserInfoByToken(@PathVariable("token") String token) {
        Long userId = Long.valueOf(jwtConfig.getUserIdFromToken(token));
        return Result.success(userInfoService.getUserInfoById(userId));
    }

    /**
     * 普通方式添加一个用户
     *
     * @param userAccount 用户实体对象
     * @return 通用返回格式
     */
    @Transactional
    @PostMapping("/create/common")
    public Result createUserCommon(@RequestBody UserAccount userAccount) {
        userAccountService.save(userAccount);
        Long userId = userAccountService.getOne(new QueryWrapper<UserAccount>().eq("account", userAccount.getAccount())).getUserId();
        userAccount.getUserInfo().setUserId(userId);
        userInfoService.save(userAccount.getUserInfo());
        return Result.success("添加用户成功!");
    }

    /**
     * 快速添加一个用户(管理员调用)
     *
     * @param accountObj 用户账号(电话号码)
     * @return 通用返回格式
     */
    @Transactional
    @PostMapping("/create/fast")
    public Result createUserFast(@RequestBody JSONObject accountObj) {
        String account = accountObj.getStr("account");
        boolean isExist = userAccountService.getBaseMapper().exists(new QueryWrapper<UserAccount>().eq("account", account));
        if (isExist) return Result.error("该账号已被注册");
        //创建用户账户对象并随机生成密码填入
        UserAccount userAccount = new UserAccount();
        userAccount.setAccount(account);
        userAccount.setPassword(UUID.randomUUID().toString().replace("-", "").substring(0, 9));
        userAccount.setAvailable(false);
        userAccountService.save(userAccount);
        //获取创建的用户编号
        Long userId = userAccountService.getOne(new QueryWrapper<UserAccount>().eq("account", userAccount.getAccount())).getUserId();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setTelephone(account);
        userInfo.setNickname("自由蝶");
        userInfoService.save(userInfo);
        return Result.success("添加用户成功,账号为:" + account);
    }

    /**
     * 更新用户信息
     *
     * @param userInfo 用户附加信息实体
     * @return 通用返回格式
     */
    @PostMapping("/modify")
    public Result modifyUser(@RequestBody @Validated UserInfo userInfo) {
        userInfoService.updateById(userInfo);
        return Result.success("更新信息成功");
    }

    /**
     * 修改用户的可用状态
     *
     * @param userId    用户编号
     * @param available 账号是否可用
     * @return 通用返回格式
     */
    @PutMapping("/modify/available/{userId}")
    public Result modifyAvailable(@PathVariable("userId") Long userId, @RequestParam Boolean available) {
        userAccountService.update(new UpdateWrapper<UserAccount>().set("available", available).eq("user_id", userId));
        return Result.success("已将该用户" + (available ? "启用" : "禁用"));
    }

    /**
     * 修改密码
     *
     * @param userAccount 用户账号实体
     * @return 通用返回格式
     */
    @PostMapping("/modify/password")
    public Result modifyPassword(@RequestBody @Validated UserAccount userAccount) {
        UpdateWrapper<UserAccount> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("password", userAccount.getPassword()).eq("user_id", userAccount.getUserId());
        userAccountService.update(updateWrapper);
        return Result.success("修改密码成功");
    }

    /**
     * 删除用户
     *
     * @param userId 用户编号
     * @return 通用返回格式
     */
    @DeleteMapping("/remove/{userId}")
    public Result removeUserById(@PathVariable("userId") Long userId) {
        userAccountService.removeById(userId);
        return Result.success("删除用户成功");
    }

    /**
     * 验证用户密码是否正确
     *
     * @param userAccount 用户账号实体
     * @return 通用返回格式
     */
    @PostMapping("/verify/password")
    public Result verifyUserPassword(@RequestBody UserAccount userAccount) {
        String password = userAccountService.getById(userAccount).getPassword();
        if (password.equals(userAccount.getPassword())) return Result.success(true);
        else return Result.success(false);
    }

    /**
     * 判断用户登录账户是否已经存在
     *
     * @param account 用户输入的登陆账号
     * @return 通用返回格式
     */
    @GetMapping("/register/exist/{account}")
    public Result userAccountExist(@PathVariable("account") String account) {
        boolean isExist = userAccountService.getBaseMapper().exists(new QueryWrapper<UserAccount>().eq("account", account));
        return Result.success(isExist);
    }

    /**
     * 用户修改自己的头像
     *
     * @param userId 管理员编号
     * @param file   头像文件
     * @param dir    文件所属文件夹
     * @return 通用返回格式
     */
    @Transactional
    @PostMapping("/avatar")
    public Result uploadUserAvatarFile(Long userId, MultipartFile file, String dir) {
        Files fileObj = filesService.uploadFile(file, dir);
        UserInfo userInfo = userInfoService.getById(userId);
        UpdateWrapper<UserInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("avatar_file_id", fileObj.getFileId()).eq("user_id", userId);
        if (userInfo.getAvatarFileId().equals("")) userInfoService.update(updateWrapper);
        else {
            Files originalFile = filesService.getById(userInfo.getAvatarFileId());
            filesService.deleteFile(originalFile.getFileId(), originalFile.getFolder());
            userInfoService.update(updateWrapper);
        }
        return Result.success("修改头像成功");
    }
}
