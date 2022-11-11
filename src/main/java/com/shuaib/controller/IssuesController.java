package com.shuaib.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuaib.bean.Issues;
import com.shuaib.common.Result;
import com.shuaib.service.IssuesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/issue")
public class IssuesController {

    @Autowired
    private IssuesService issuesService;

    /**
     * 分页获取留言列表
     *
     * @param currentPage 当前页码
     * @param pageSize    页面大小
     * @return 通用返回格式
     */
    @GetMapping("/list")
    public Result getIssueListPage(int currentPage, int pageSize) {
        Page<Issues> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Issues> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("create_time");
        List<Issues> issuesList = issuesService.getBaseMapper().selectPage(page, queryWrapper).getRecords();
        return Result.success(issuesList);
    }

    /**
     * 获取留言总数
     *
     * @return 通用返回格式
     */
    @GetMapping("/count")
    public Result getIssueCount() {
        return Result.success(issuesService.count());
    }

    /**
     * 用户发表留言
     *
     * @param issues 消息信息实体，包含消息信息内容
     * @return 通用返回格式
     */
    @PostMapping("/create")
    public Result createIssue(@RequestBody @Validated Issues issues) {
        issuesService.save(issues);
        return Result.success("用户发表消息信息成功");
    }

    /**
     * 根据编号获取留言的具体信息
     *
     * @param issueId 消息信息编号
     * @return 通用返回格式
     */
    @GetMapping("/info/{issueId}")
    public Result getIssueById(@PathVariable("issueId") Long issueId) {
        return Result.success(issuesService.getIssueInfoById(issueId));
    }

    /**
     * 管理员接取留言
     *
     * @param issueId 留言编号
     * @param adminId 管理员编号
     * @return 通用返回格式
     */
    @PutMapping("/take/{issueId}")
    public Result takeIssueAdmin(@PathVariable("issueId") Long issueId, @RequestParam Long adminId) {
        UpdateWrapper<Issues> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("admin_id", adminId).set("state", 1).eq("issue_id", issueId);
        issuesService.update(updateWrapper);
        return Result.success("该问题已成功被编号为:" + adminId + "业务员接取");
    }

    /**
     * 根据编号删除一条留言
     *
     * @param issueId 留言编号
     * @return 通用返回格式
     */
    @DeleteMapping("/delete/{issueId}")
    public Result deleteIssueById(@PathVariable("issueId") Long issueId) {
        issuesService.removeById(issueId);
        return Result.success("该留言已被删除");
    }

    /**
     * 根据编号关闭一个留言
     * @param issueId 留言编号
     * @return 通用返回格式
     */
    @PutMapping("/close/{issueId}")
    public Result closeIssueById(@PathVariable("issueId") Long issueId) {
        UpdateWrapper<Issues> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("state", 2).eq("issue_id", issueId);
        issuesService.update(updateWrapper);
        return Result.success("该留言已关闭");
    }

}
