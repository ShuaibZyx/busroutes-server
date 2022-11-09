package com.shuaib.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuaib.bean.Notices;
import com.shuaib.common.Result;
import com.shuaib.service.NoticesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/notice")
public class NoticesController {
    @Autowired
    private NoticesService noticesService;

    /**
     * 分页获取所有公告信息
     * @param currentPage 当前页码
     * @param pageSize 页面大小
     * @return 通用返回格式
     */
    @RequestMapping("/list")
    public Result getNoticeListPage(int currentPage, int pageSize) {
        return Result.success(noticesService.getBaseMapper().selectPage(new Page<>(currentPage, pageSize), new QueryWrapper<Notices>().orderByDesc("create_time")).getRecords());
    }

    /**
     * 获取留言总数量
     * @return 通用返回格式
     */
    @GetMapping("/count")
    public Result getNoticeCount(){
        return Result.success(noticesService.count());
    }

    /**
     * 添加一个公告
     * @param notices 公告信息对象
     * @return 通用返回格式
     */
    @PostMapping("/create")
    public Result createNotice(@RequestBody @Validated Notices notices){
        noticesService.save(notices);
        return Result.success("创建公告成功");
    }

    /**
     * 删除一个公告
     * @param noticeId 公告编号
     * @return 通用返回格式
     */
    @DeleteMapping("/remove/{noticeId}")
    public Result removeNoticeById(@PathVariable("noticeId")Long noticeId){
        noticesService.removeById(noticeId);
        return Result.success("删除公告成功");
    }

    /**
     * 更新一个公告信息
     * @param notices 公告信息对象
     * @return 通用返回格式
     */
    @PostMapping("/modify")
    public Result updateNoticeInfo(@RequestBody @Validated Notices notices){
        noticesService.updateById(notices);
        return Result.success("更新公告信息成功");
    }

    /**
     * 根据公告编号获取单个公告详细信息
     * @param noticeId 公告编号
     * @return 通用返回格式
     */
    @GetMapping("/info/{noticeId}")
    public Result getNoticeInfoById(@PathVariable("noticeId") Long noticeId){
        return Result.success(noticesService.getById(noticeId));
    }



}
