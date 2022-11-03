package com.shuaib.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuaib.bean.Admin;
import com.shuaib.bean.Messages;
import com.shuaib.bean.UserAccount;
import com.shuaib.common.Result;
import com.shuaib.service.IssuesService;
import com.shuaib.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessagesController {
    @Autowired
    private MessagesService messagesService;

    /**
     * 分页获取消息列表
     * @param currentPage 当前页码
     * @param pageSize 页面大小
     * @return 通用返回格式
     */
    /*@GetMapping("list")
    public Result getMessageListPage(int currentPage, int pageSize){
        return Result.success(messagesService.getBaseMapper().selectPage(new Page<>(currentPage,pageSize),new QueryWrapper<Messages>().orderByDesc("create_time")).getRecords());
    }*/
    /**
     * 用户添加留言
     * @param messages 留言消息实体，包含留言信息内容
     * @return 通用返回格式
     */
    @PostMapping("/createUserMessage")
    public Result createUserMessage(@RequestBody Messages messages){
        messagesService.save(messages);
        return Result.success("用户留言添加成功");
    }
    /**
     * 管理员添加留言
     * @param messages 留言消息实体，包含留言消息内容
     * @return 通用返回格式
     */
    @PostMapping("/createAdminMessage")
    public Result createAdminMessage(@RequestBody Messages messages){
        messages.setSenderRole("admin");
        messagesService.save(messages);
        return Result.success("管理员留言添加成功");
    }
    /**
     * 根据messageId获取留言信息
     * @param messageId
     * @return
     */
    @GetMapping("/info")
    public Result getMessageById(Long messageId){
        return Result.success(messagesService.getById(messageId));
    }





}
