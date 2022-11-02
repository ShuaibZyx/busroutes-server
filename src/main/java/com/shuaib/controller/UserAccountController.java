package com.shuaib.controller;

import com.shuaib.bean.UserAccount;
import com.shuaib.common.Result;
import com.shuaib.service.UserAccountService;
import com.shuaib.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     分页获取用户信息列表
     * @param currentPage 当前页码
     * @param pageSize 页面大小
     * @return 通用返回格式
     */
    @GetMapping("/list")
    public Result getUserListPage(int currentPage, int pageSize) {
        return Result.success(userAccountService.getUserListPage(currentPage, pageSize));
    }

    /**
     * 添加一个用户
     * @param userAccount 用户对象实体，包含用户基本信息与附加信息
     * @return 通用返回格式
     */
    @Transactional
    @PostMapping("/add")
    public Result addUser(@RequestBody UserAccount userAccount){
        userAccountService.save(userAccount);
        userInfoService.save(userAccount.getUserInfo());
        return Result.success("添加用户成功");
    }

    @PostMapping("/delete")
    public Result deleteUser(Long userId){
        userAccountService.removeById(userId);
        return Result.success("删除用户成功");
    }


}
