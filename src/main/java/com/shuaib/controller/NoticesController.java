package com.shuaib.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuaib.bean.NoticeReads;
import com.shuaib.bean.Notices;
import com.shuaib.common.Result;
import com.shuaib.service.NoticeReadsService;
import com.shuaib.service.NoticesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Validated
@RestController
@RequestMapping("/notice")
public class NoticesController {
    @Autowired
    private NoticesService noticesService;

    @Autowired
    private NoticeReadsService noticeReadsService;

    /**
     * 分页获取所有公告信息
     *
     * @param currentPage 当前页码
     * @param pageSize    页面大小
     * @return 通用返回格式
     */
    @RequestMapping("/list")
    public Result getNoticeListPage(int currentPage, int pageSize) {
        return Result.success(noticesService.getBaseMapper().selectPage(new Page<>(currentPage, pageSize), new QueryWrapper<Notices>().orderByDesc("create_time")).getRecords());
    }

    /**
     * 获取公告总数量
     *
     * @return 通用返回格式
     */
    @GetMapping("/count")
    public Result getNoticeCount() {
        return Result.success(noticesService.count());
    }

    /**
     * 添加一个公告
     *
     * @param notices 公告信息对象
     * @return 通用返回格式
     */
    @PostMapping("/create")
    public Result createNotice(@RequestBody @Validated Notices notices) {
        noticesService.save(notices);
        return Result.success("创建公告成功");
    }

    /**
     * 删除一个公告
     *
     * @param noticeId 公告编号
     * @return 通用返回格式
     */
    @DeleteMapping("/remove/{noticeId}")
    public Result removeNoticeById(@PathVariable("noticeId") Long noticeId) {
        noticesService.removeById(noticeId);
        return Result.success("删除公告成功");
    }

    /**
     * 更新一个公告信息
     *
     * @param notices 公告信息对象
     * @return 通用返回格式
     */
    @PostMapping("/modify")
    public Result updateNoticeInfo(@RequestBody @Validated Notices notices) {
        noticesService.updateById(notices);
        return Result.success("更新公告信息成功");
    }

    /**
     * 根据公告编号获取单个公告详细信息
     *
     * @param noticeId 公告编号
     * @return 通用返回格式
     */
    @GetMapping("/info/{noticeId}")
    public Result getNoticeInfoById(@PathVariable("noticeId") Long noticeId) {
        return Result.success(noticesService.getById(noticeId));
    }

    /**
     * 根据编号获取某用户应该读的最新公告信息
     *
     * @param userId 用户编号
     * @return 用户该读的公告信息
     */
    @GetMapping("/info/new/{userId}")
    public Result getLatestNoticeInfo(@PathVariable("userId") Long userId) {
        JSONObject jsonObject = new JSONObject();
        Notices latestNotice = noticesService.getBaseMapper().selectList(new QueryWrapper<Notices>().eq("state", 1).orderByDesc("update_time")).get(0);
        NoticeReads noticeReads = noticeReadsService.getBaseMapper().selectOne(new QueryWrapper<NoticeReads>().select("state").eq("user_id", userId).eq("notice_id", latestNotice.getNoticeId()));
        jsonObject.put("latestNotice", latestNotice);
        jsonObject.put("state", noticeReads.getState());
        return Result.success(jsonObject);
    }

    /**
     * 根据编号获取用户相关的所有公告信息以及阅读状态
     *
     * @param userId 用户编号
     * @return 公告信息及用户阅读状态数组
     */
    @GetMapping("/list/published/{userId}")
    public Result getUserNoticeList(@PathVariable("userId") Long userId) {
        List<Notices> noticeList = noticesService.getBaseMapper().selectList(new QueryWrapper<Notices>().eq("state", 1).orderByDesc("update_time"));
        List<JSONObject> UserNoticeList = new ArrayList<>();
        for (Notices notice : noticeList) {
            JSONObject jsonObject = new JSONObject();
            NoticeReads noticeReads = noticeReadsService.getBaseMapper().selectOne(new QueryWrapper<NoticeReads>().select("state").eq("user_id", userId).eq("notice_id", notice.getNoticeId()));
            jsonObject.put("info", notice);
            jsonObject.put("state", noticeReads.getState());
            UserNoticeList.add(jsonObject);
        }
        return Result.success(UserNoticeList);
    }
}
