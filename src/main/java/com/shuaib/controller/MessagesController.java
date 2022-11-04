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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/message")
public class MessagesController {
    @Autowired
    private MessagesService messagesService;
    /**
     * 用户发送消息
     * @param messages 消息实体，包含留言信息内容
     * @return 通用返回格式
     */
    @PostMapping("/createUserMessage")
    public Result createUserMessage(@RequestBody Messages messages) {
        messagesService.save(messages);
        return Result.success("用户消息添加成功");
    }
    /**
     * 管理员发送消息
     * @param messages 消息实体，包含留言消息内容
     * @return 通用返回格式
     */
    @PostMapping("/createAdminMessage")
    public Result createAdminMessage(@RequestBody Messages messages) {
        messages.setSenderRole("admin");
        messagesService.save(messages);
        return Result.success("管理员消息添加成功");
    }

}
