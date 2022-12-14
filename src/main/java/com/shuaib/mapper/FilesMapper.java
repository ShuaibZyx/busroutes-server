package com.shuaib.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuaib.bean.Files;
import org.apache.ibatis.annotations.Mapper;

/**
* @author shuai   b
* @description 针对表【files】的数据库操作Mapper
* @createDate 2022-11-01 14:40:00
* @Entity com.shuaib.bean.Files
*/
@Mapper
public interface FilesMapper extends BaseMapper<Files> {

}




