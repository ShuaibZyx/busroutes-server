package com.shuaib.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuaib.bean.Admin;
import com.shuaib.common.Result;
import com.shuaib.common.jwt.JwtConfig;
import com.shuaib.service.AdminService;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private JwtConfig jwtConfig;

    @Autowired
    private AdminService adminService;

    /**
     * 管理员登录接口
     *
     * @param loginObject 登录所需要的参数
     * @return 通用返回格式
     */
    @PostMapping("/login")
    public Result adminLogin(@RequestBody JSONObject loginObject) {
        String account = loginObject.getStr("account");
        String password = loginObject.getStr("password");
        Long expire = loginObject.getLong("expire");
        JSONObject json = new JSONObject();
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        Admin admin = adminService.getBaseMapper().selectOne(queryWrapper);
        if (admin == null) return Result.error("账号不存在");
        if (!password.equals(admin.getPassword())) return Result.error("密码错误");
        // 这里把userId转为String类型,实际开发中如果subject需要存userId，则可以JwtConfig的createToken方法的参数设置为Long类型
        jwtConfig.setExpire(expire);
        String token = jwtConfig.createToken(admin.getAdminId().toString());
        if (!StringUtils.isEmpty(token)) {
            json.set("token", token);
        }
        return Result.success(200, "登陆成功", json);
    }

    /**
     * 分页获取普通管理员信息列表
     *
     * @param currentPage 当前页码
     * @param pageSize    页面大小
     * @return 通用返回格式
     */
    @GetMapping("/list")
    public Result getAdminListPage(int currentPage, int pageSize) {
        System.out.println(pageSize);
        System.out.println(currentPage);
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("admin_id", "666666666").orderByDesc("create_time");
        Page<Admin> page = new Page<>(currentPage, pageSize);
        return Result.success(adminService.getBaseMapper().selectPage(page, queryWrapper).getRecords());
    }

    /**
     * 获取所有普通管理员数量
     * @return 通用返回格式
     */
    @GetMapping("/count")
    public Result getAdminCount(){
        return Result.success(adminService.count(new QueryWrapper<Admin>().ne("admin_id", "666666666")));
    }

    /**
     * 根据token获取当前管理员的信息
     *
     * @param token 令牌字符串
     * @return 通用返回格式
     */
    @GetMapping("/info/token/{token}")
    public Result getAdminInfoByToken(@PathVariable("token") String token) {
        Long adminId = Long.valueOf(jwtConfig.getUserIdFromToken(token));
        return Result.success(adminService.getAdminInfoById(adminId));
    }

    /**
     * 根据id获取当前管理员的信息
     *
     * @param adminId 管理员Id
     * @return 通用返回格式
     */
    @GetMapping("/info/id/{adminId}")
    public Result getAdminInfoById(@PathVariable("adminId") Long adminId) {
        return Result.success(adminService.getAdminInfoById(adminId));
    }

    /**
     * 创建一个管理员
     *
     * @param admin 管理员实体
     * @return 通用返回格式
     */
    @PostMapping("/create")
    public Result createAdmin(@RequestBody @Validated Admin admin) {
        adminService.save(admin);
        return Result.success("创建管理员成功");
    }

    /**
     * 快速创建一个管理员
     *
     * @param adminObj 管理员电话
     * @return 通用返回格式
     */
    @PostMapping("/create/fast")
    public Result createAdminFast(@RequestBody JSONObject adminObj) {
        String telephone = adminObj.getStr("telephone");
        Admin admin = new Admin();
        admin.setTelephone(telephone);
        admin.setAccount(UUID.randomUUID().toString().replace("-", "").substring(0, 9));
        admin.setPassword("absolutelyShuaib");
        adminService.save(admin);
        return Result.success("创建管理员成功,账号为:" + admin.getAccount());
    }

    /**
     * 超级管理员更新管理员信息
     * @param admin 普通管理员实体对象
     * @return 通用返回格式
     */
    @PostMapping("/modify")
    public Result modifyAdmin(@RequestBody @Validated Admin admin) {
        UpdateWrapper<Admin> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("account", admin.getAccount()).set("password", admin.getPassword())
                .set("power", admin.getPower()).set("telephone", admin.getTelephone())
                .eq("admin_id", admin.getAdminId());
        adminService.update(updateWrapper);
        return Result.success("更新管理员信息成功");
    }

    /**
     * 普通管理员自己修改密码
     *
     * @param adminId     管理员编号
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return 通用返回格式
     */
    @PostMapping("/modify/password")
    public Result modifyPassword(@NotNull Long adminId,
                                 @Length(min = 6, max = 17, message = "管理员密码应在6~17位") String oldPassword,
                                 @Length(min = 6, max = 17, message = "管理员密码应在6~17位") String newPassword) {
        Admin admin = adminService.getById(adminId);
        if (admin == null) return Result.error("账号不存在");
        if (!admin.getPassword().equals(oldPassword)) return Result.error("原密码不正确");
        admin.setPassword(newPassword);
        adminService.updateById(admin);
        return Result.success(401, "修改密码成功");
    }

    /**
     * 修改管理员电话号码
     *
     * @param adminId   管理员编号
     * @param telephone 将要修改的电话号码
     * @return 通用返回格式
     */
    @PutMapping("/modify/phone/{adminId}")
    public Result modifyTelephone(@PathVariable("adminId") Long adminId,
                                  @Pattern(regexp = "^[1][3,4,5,7,8][0-9]{9}$", message = "手机号码格式有误")
                                  @Length(min = 11, max = 11, message = "手机号码必须为11位") String telephone) {
        UpdateWrapper<Admin> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("telephone", telephone).eq("admin_id", adminId);
        adminService.update(updateWrapper);
        return Result.success("修改电话号码成功");
    }


    /**
     * 超级管理员重置普通管理员密码
     *
     * @param adminId 管理员编号
     * @return 通用返回格式
     */
    @PutMapping("/reset/password/{adminId}")
    public Result resetPassword(@PathVariable("adminId") Long adminId) {
        UpdateWrapper<Admin> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("password", "absolutelyShuaib").eq("admin_id", adminId);
        adminService.update(updateWrapper);
        return Result.success("重置密码成功");
    }

    /**
     * 删除一个管理员
     *
     * @param adminId 管理员编号
     * @return 通用返回格式
     */
    @DeleteMapping("/remove/{adminId}")
    public Result removeAdminById(@PathVariable("adminId") Long adminId) {
        adminService.removeById(adminId);
        return Result.success("删除管理员成功");
    }

    /**
     * 验证管理员账号是否存在
     *
     * @param account 管理员账号
     * @return 通用返回格式
     */
    @GetMapping("/exist/{account}")
    public Result adminAccountExist(@PathVariable("account") String account) {
        boolean isExist = adminService.getBaseMapper().exists(new QueryWrapper<Admin>().eq("account", account));
        return Result.success(isExist);
    }
}
