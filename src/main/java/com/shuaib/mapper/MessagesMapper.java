package com.shuaib.mapper;

import com.shuaib.bean.Messages;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author shuai   b
* @description 针对表【messages】的数据库操作Mapper
* @createDate 2022-11-02 14:04:47
* @Entity com.shuaib.bean.Messages
*/
@Mapper
public interface MessagesMapper extends BaseMapper<Messages> {

}




