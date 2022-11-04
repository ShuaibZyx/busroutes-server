package com.shuaib.service;

import com.shuaib.bean.Issues;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author shuai   b
* @description 针对表【issues】的数据库操作Service
* @createDate 2022-11-02 14:04:47
*/
public interface IssuesService extends IService<Issues> {
    /**
     * 获取消息信息内容
     * @param issueId 消息信息编号
     * @return 消息信息内容
     */
    Issues getIssueInfoById(Long issueId);
}
