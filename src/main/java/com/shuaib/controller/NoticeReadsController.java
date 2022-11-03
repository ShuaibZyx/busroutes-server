package com.shuaib.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shuaib.bean.NoticeReads;
import com.shuaib.bean.UserAccount;
import com.shuaib.common.Result;
import com.shuaib.service.NoticeReadsService;
import com.shuaib.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/noticeReads")
public class NoticeReadsController {
    @Autowired
    private NoticeReadsService noticeReadsService;

    @Autowired
    private UserAccountService userAccountService;

    /**
     * 发布公告
     * @param noticeId 公告编号
     * @return 通用返回格式
     */
    @Transactional
    @PostMapping("/publish")
    public Result publishNotice(Long noticeId) {
        List<UserAccount> userIdList = userAccountService.getBaseMapper().selectList(new QueryWrapper<UserAccount>().select("user_id").orderByDesc("create_time"));
        List<NoticeReads> noticeReadsList = new ArrayList<>();
        for (UserAccount userAccount : userIdList) {
            NoticeReads noticesRead = new NoticeReads();
            noticesRead.setUserId(userAccount.getUserId());
            noticesRead.setNoticeId(noticeId);
            noticesRead.setState(false);
            noticeReadsList.add(noticesRead);
        }
        noticeReadsService.saveOrUpdateBatch(noticeReadsList);
        return Result.success("公告发布成功");
    }


    /**
     * 用户阅读公告
     * @param noticeReads 阅读记录对象
     * @return 通用返回格式
     */
    @PostMapping("/userView")
    public Result noticeUserView(@RequestBody NoticeReads noticeReads) {
        noticeReads.setState(true);
        noticeReadsService.updateById(noticeReads);
        return Result.success("阅读记录状态修改成功");
    }

    /**
     * 设置某公告为全部已读
     * @param noticeId 公告编号
     * @return 通用返回格式
     */
    @PutMapping("/singleViewAll/{noticeId}")
    public Result noticeViewAll(@PathVariable("noticeId") Long noticeId) {
        UpdateWrapper<NoticeReads> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("state", true).eq("notice_id", noticeId);
        noticeReadsService.update(updateWrapper);
        return Result.success("该公告全部已读");
    }

    /**
     * 设置某用户的所有公告已读
     * @param userId 用户编号
     * @return 通用返回格式
     */
    @PutMapping("/userViewAll/{userId}")
    public Result userViewAll(@PathVariable("userId") Long userId) {
        UpdateWrapper<NoticeReads> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("state", true).eq("user_id", userId);
        noticeReadsService.update(updateWrapper);
        return Result.success("该用户全部公告已读");
    }

    /**
     * 获取某公告的总发送用户数和总阅读数
     * @param noticeId 公告编号
     * @return 通用返回格式
     */
    @GetMapping("/userCount/{noticeId}")
    public Result noticeUserCount(@PathVariable("noticeId") Long noticeId) {
        Long userCount = noticeReadsService.getBaseMapper().selectCount(new QueryWrapper<NoticeReads>().eq("notice_id", noticeId));
        Long viewCount = noticeReadsService.getBaseMapper().selectCount(new QueryWrapper<NoticeReads>().eq("notice_id", noticeId).eq("state", true));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userCount", userCount);
        jsonObject.put("viewCount", viewCount);
        return Result.success(jsonObject);
    }
}
