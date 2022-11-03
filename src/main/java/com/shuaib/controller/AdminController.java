package com.shuaib.controller;


import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuaib.bean.Admin;
import com.shuaib.common.Result;
import com.shuaib.common.jwt.JwtConfig;
import com.shuaib.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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
     * @param account  管理员账号
     * @param password 管理员密码
     * @return 通用返回格式
     */
    @PostMapping("/login")
    public Result adminLogin(String account, String password) {
        JSONObject json = new JSONObject();
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        Admin admin = adminService.getBaseMapper().selectOne(queryWrapper);
        if (admin == null) return Result.error("账号不存在");
        if (!password.equals(admin.getPassword())) return Result.error("密码错误");
        // 这里把userId转为String类型，实际开发中如果subject需要存userId，则可以JwtConfig的createToken方法的参数设置为Long类型
        String token = jwtConfig.createToken(admin.getAdminId().toString());
        String adminId = jwtConfig.getUserIdFromToken(token);
        if (!StringUtils.isEmpty(token)) {
            json.set("token", token);
            json.set("adminId", adminId);
        }
        return Result.success(json);
    }

    /**
     * 分页获取普通管理员信息列表
     * @param currentPage 当前页码
     * @param pageSize    页面大小
     * @return 通用返回格式
     */
    @GetMapping("/list")
    public Result getAdminListPage(int currentPage, int pageSize) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("admin_id", "666666666").orderByDesc("create_time");
        Page<Admin> page = new Page<>(currentPage, pageSize);
        return Result.success(adminService.getBaseMapper().selectPage(page, queryWrapper).getRecords());
    }

    /**
     * 创建一个管理员
     *
     * @param admin 管理员实体
     * @return 通用返回格式
     */
    @PostMapping("/create")
    public Result createAdmin(@RequestBody Admin admin) {
        adminService.save(admin);
        return Result.success("创建管理员成功");
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
     * 普通管理员自己修改密码
     *
     * @param adminId     管理员编号
     * @param oldPassword 原密码
     * @param newPassword 新密码
     * @return 通用返回格式
     */
    @PostMapping("/modify/password")
    public Result modifyPassword(Long adminId, String oldPassword, String newPassword) {
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
     * @param admin 管理员实体
     * @return 通用返回格式
     */
    @PostMapping("/modify/phone")
    public Result modifyTelephone(@RequestBody Admin admin) {
        adminService.updateById(admin);
        return Result.success("修改电话号码成功");
    }

    /**
     * 超级管理员修改普通管理员用户权限
     *
     * @param admin 管理员实体
     * @return 通用返回格式
     */
    @PostMapping("/modify/role")
    public Result modifyRole(@RequestBody Admin admin) {
        adminService.updateById(admin);
        return Result.success("修改权限成功");
    }

    /**
     * 超级管理员重置普通管理员密码
     *
     * @param admin 管理员实体
     * @return 通用返回格式
     */
    @PostMapping("/reset/password")
    public Result resetPassword(@RequestBody Admin admin) {
        admin.setPassword("absolutelyShuaib");
        return Result.success("重置密码成功");
    }
}
