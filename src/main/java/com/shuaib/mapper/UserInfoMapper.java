package com.shuaib.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuaib.bean.Files;
import com.shuaib.bean.UserInfo;
import org.apache.ibatis.annotations.*;

/**
 * @author shuai   b
 * @description 针对表【user_info】的数据库操作Mapper
 * @createDate 2022-11-01 14:40:00
 * @Entity com.shuaib.bean.UserInfo
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {
    /**
     * 根据用户编号查询用户附加信息
     * @param userId 用户编号
     * @return 通用返回格式
     */
    @Select("select * from user_info where user_id = #{userId}")
    @Results({
            @Result(column = "avatar_file_id", property = "avatarFileId"),
            @Result(column = "avatar_file_id", property = "avatarFile", javaType = Files.class,
                    one = @One(select = "com.shuaib.mapper.FilesMapper.selectById"))
    })
    UserInfo getUserInfoById(Long userId);
}




