package com.shuaib.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuaib.bean.Issues;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuaib.bean.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author shuai   b
 * @description 针对表【issues】的数据库操作Mapper
 * @createDate 2022-11-02 14:04:47
 * @Entity com.shuaib.bean.Issues
 */
@Mapper
public interface IssuesMapper extends BaseMapper<Issues> {
    /**
     * 获取消息信息
     * @param issueId 消息信息编号
     * @return 消息信息查询结果
     */
    @Select("select * from issues where issue_id = #{issueId}")
    @Results({
            @Result(column = "creater_id", property = "createrId"),
            @Result(column = "creater_id", property = "userInfo", javaType = UserInfo.class,
                    one = @One(select = "com.shuaib.mapper.UserInfoMapper.getUserInfoById")
            ),
            @Result(column = "issue_id", property = "issueId", id = true),
            @Result(column = "issue_id", property = "messageList", javaType = List.class,
                    many = @Many(select = "com.shuaib.mapper.MessagesMapper.getMessageListByIssueId"))
    })
    Issues getIssueInfoById(Long issueId);
}




