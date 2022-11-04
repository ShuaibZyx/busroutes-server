package com.shuaib.mapper;

import com.shuaib.bean.Messages;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author shuai   b
* @description 针对表【messages】的数据库操作Mapper
* @createDate 2022-11-02 14:04:47
* @Entity com.shuaib.bean.Messages
*/
@Mapper
public interface MessagesMapper extends BaseMapper<Messages> {
    /**
     * 获取留消息
     * @param issueId 消息信息编号
     * @return 消息查询结果
     */
    @Select("select * from messages where issue_id = #{issueId} order by create_time asc")
    List<Messages> getMessageListByIssueId(Long issueId);
}




