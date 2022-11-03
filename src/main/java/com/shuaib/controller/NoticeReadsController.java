package com.shuaib.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shuaib.bean.NoticeReads;
import com.shuaib.bean.UserAccount;
import com.shuaib.common.Result;
import com.shuaib.service.NoticeReadsService;
import com.shuaib.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/notice/reads")
public class NoticeReadsController {
    @Autowired
    private NoticeReadsService noticeReadsService;

    @Autowired
    private UserAccountService userAccountService;

    /**
     * 发布公告
     *
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
    @PostMapping("/view")
    public Result noticeUserView(@RequestBody NoticeReads noticeReads) {
        noticeReads.setState(true);
        noticeReadsService.updateById(noticeReads);
        return null;
    }
}
