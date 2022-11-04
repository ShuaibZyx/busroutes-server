package com.shuaib.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuaib.bean.Issues;
import com.shuaib.bean.UserInfo;
import com.shuaib.common.Result;
import com.shuaib.service.IssuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/issue")
public class IssuesController {

    @Autowired
    private IssuesService issuesService;
    /**
     * 获取消息
     * @param currentPage 当前页码
     * @param pageSize 页面大小
     * @return 通用返回格式
     */
    @GetMapping("/list")
    public Result getIssueListPage(int currentPage, int pageSize) {
        Page<Issues> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Issues> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        List<Issues> issuesList = issuesService.getBaseMapper().selectPage(page, queryWrapper).getRecords();
        return Result.success(issuesList);
    }
    /**
     * 用户发表留言
     * @param issues 消息信息实体，包含消息信息内容
     * @return 通用返回格式
     */
    @PostMapping("/create")
    public Result createIssue(@RequestBody Issues issues) {
        issuesService.save(issues);
        return Result.success("用户发表消息信息成功");
    }
    /**
     * 查看消息信息
     * @param issueId 消息信息编号
     * @return 通用返回格式
     */
    @GetMapping("/info/{issueId}")
    public Result getIssueById(@PathVariable("issueId") Long issueId) {
        return Result.success(issuesService.getIssueInfoById(issueId));
    }


}
