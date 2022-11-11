package com.shuaib.controller;

import com.shuaib.bean.Messages;
import com.shuaib.common.Result;
import com.shuaib.service.MessagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/message")
public class MessagesController {
    @Autowired
    private MessagesService messagesService;

    /**
     * 用户发送消息
     *
     * @param message 消息实体，包含留言信息内容
     * @return 通用返回格式
     */
    @PostMapping("/createUserMessage")
    public Result createUserMessage(@RequestBody Messages message) {
        messagesService.save(message);
        return Result.success("发送消息成功");
    }

    /**
     * 管理员发送消息
     *
     * @param message 消息实体，包含留言消息内容
     * @return 通用返回格式
     */
    @PostMapping("/createAdminMessage")
    public Result createAdminMessage(@RequestBody Messages message) {
        message.setSenderRole("admin");
        messagesService.save(message);
        return Result.success("发送消息成功(管理员)");
    }

}
