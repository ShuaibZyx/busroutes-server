package com.shuaib.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuaib.bean.Admin;
import com.shuaib.bean.Files;
import org.apache.ibatis.annotations.*;

import static net.sf.jsqlparser.parser.feature.Feature.select;
import static net.sf.jsqlparser.util.validation.metadata.NamedObject.column;

/**
 * @author shuai   b
 * @description 针对表【admin】的数据库操作Mapper
 * @createDate 2022-11-01 14:40:00
 * @Entity com.shuaib.bean.Admin
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {
    @Select("select * from admin where admin_id = #{adminId}")
    @Results({
            @Result(column = "avatar_file_id", property = "avatarFileId"),
            @Result(column = "avatar_file_id", property = "avatarFile", javaType = Files.class,
                    one = @One(select = "com.shuaib.mapper.FilesMapper.selectById")
            )
    })
    Admin getAdminInfoById(Long adminId);
}




