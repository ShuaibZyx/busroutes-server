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
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
     *
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
    public Result createAdmin(@RequestBody @Validated Admin admin) {
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
     * 修改普通管理员权限
     *
     * @param adminId 管理员编号
     * @param power   权限级别数字
     * @return 通用返回格式
     */
    @PutMapping("/modify/role/{adminId}")
    public Result modifyRole(@PathVariable(value = "adminId") Long adminId, @Range(min = 0, max = 3, message = "权限数字应在0~3之间") Integer power) {
        UpdateWrapper<Admin> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("power", power).eq("admin_id", adminId);
        adminService.update(updateWrapper);
        return Result.success("修改权限成功");
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
}
