package com.shuaib.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shuaib.common.jwt.JwtConfig;
import com.shuaib.bean.UserAccount;
import com.shuaib.bean.UserInfo;
import com.shuaib.common.Result;
import com.shuaib.service.UserAccountService;
import com.shuaib.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserInfoService userInfoService;

    @Resource
    private JwtConfig jwtConfig;

    /**
     * 用户注册方法
     * @param userAccount 用户账户对象
     * @return 通用返回格式
     */
    @Transactional
    @PostMapping("/register")
    public Result register(@RequestBody UserAccount userAccount) {
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
     * @param account  用户登录账号
     * @param password 用户登录密码
     * @return 通用返回格式
     */
    @PostMapping("/login")
    public Result login(String account, String password) {
        JSONObject json = new JSONObject();
        QueryWrapper<UserAccount> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        UserAccount userAccount = userAccountService.getBaseMapper().selectOne(queryWrapper);
        if (userAccount == null) return Result.error("账号不存在,请先注册");
        if (!userAccount.getAvailable()) return Result.error("账号已冻结,如需继续使用请联系管理员");
        if (!password.equals(userAccount.getPassword())) return Result.error("密码错误");
        String token = jwtConfig.createToken(userAccount.getUserId().toString());
        String userId = jwtConfig.getUserIdFromToken(token);
        if (!StringUtils.isEmpty(token)) {
            json.set("token", token);
            json.set("userId", userId);
        }
        return Result.success(json);
    }

    /**
     * 分页获取用户信息列表
     * @param currentPage 当前页码
     * @param pageSize    页面大小
     * @return 通用返回格式
     */
    @GetMapping("/list")
    public Result getUserListPage(int currentPage, int pageSize) {
        return Result.success(userAccountService.getUserListPage(currentPage, pageSize));
    }

    /**
     * 根据Id获取用户
     * @param userId 用户编号
     * @return 通用返回格式
     */
    @GetMapping("/info/{userId}")
    public Result getUserInfoById(@PathVariable("userId") Long userId) {
        return Result.success(userInfoService.getById(userId));
    }

    /**
     * 添加一个用户(管理员调用)
     * @param accountObj 用户账号(电话号码)
     * @return 通用返回格式
     */
    @Transactional
    @PostMapping("/create")
    public Result createUser(@RequestBody JSONObject accountObj) {
        String account = accountObj.getStr("account");
        //创建用户账户对象并随机生成密码填入
        UserAccount userAccount = new UserAccount();
        userAccount.setAccount(account);
        userAccount.setPassword(UUID.randomUUID().toString().replace("-", "").substring(0, 8));
        userAccount.setAvailable(false);
        userAccountService.save(userAccount);
        //获取创建的用户编号
        Long userId = userAccountService.getOne(new QueryWrapper<UserAccount>().eq("account", userAccount.getAccount())).getUserId();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setTelephone(account);
        userInfo.setNickname("自由蝶");
        userInfoService.save(userInfo);
        return Result.success("添加用户成功");
    }

    /**
     * 删除用户
     * @param userId 用户编号
     * @return 通用返回格式
     */
    @DeleteMapping("/remove/{userId}")
    public Result removeUserById(@PathVariable("userId") Long userId) {
        userAccountService.removeById(userId);
        return Result.success("删除用户成功");
    }

    /**
     * 更新用户信息
     * @param userInfo 用户附加信息实体
     * @return 通用返回格式
     */
    @PostMapping("/update")
    public Result updateUser(@RequestBody UserInfo userInfo) {
        userInfoService.updateById(userInfo);
        return Result.success("更新用户信息成功");
    }

    /**
     * 验证用户密码是否正确
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
     * 修改密码
     * @param userAccount 用户账号实体
     * @return 通用返回格式
     */
    @PostMapping("/modify/password")
    public Result modifyPassword(@RequestBody UserAccount userAccount) {
        UpdateWrapper<UserAccount> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("password", userAccount.getPassword()).eq("user_id", userAccount.getUserId());
        userAccountService.update(updateWrapper);
        return Result.success("修改密码成功");
    }

    /**
     * 判断用户登录账户是否以及存在
     * @param accountObj 用户登陆账号
     * @return 通用返回格式
     */
    @PostMapping("/exist")
    public Result userAccountExist(@RequestBody JSONObject accountObj){
        String account = accountObj.getStr("account");
        boolean isExist = userAccountService.getBaseMapper().exists(new QueryWrapper<UserAccount>().eq("account", account));
        return Result.success(isExist);
    }
}
