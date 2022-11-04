package com.shuaib.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaib.bean.Issues;
import com.shuaib.service.IssuesService;
import com.shuaib.mapper.IssuesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
* @author shuai   b
* @description 针对表【issues】的数据库操作Service实现
* @createDate 2022-11-02 14:04:47
*/
@Service
public class IssuesServiceImpl extends ServiceImpl<IssuesMapper, Issues>
    implements IssuesService{
    @Autowired
    private IssuesMapper issuesMapper;

    /**
     * 获取消息信息内容
     * @param issueId 消息信息编号
     * @return 消息信息内容
     */
    @Override
    public Issues getIssueInfoById(Long issueId) {
        return issuesMapper.getIssueInfoById(issueId);
    }
}




