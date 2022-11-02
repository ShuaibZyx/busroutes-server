package com.shuaib.mapper;

import com.shuaib.bean.Stations;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author shuai   b
* @description 针对表【stations】的数据库操作Mapper
* @createDate 2022-11-02 14:04:47
* @Entity com.shuaib.bean.Stations
*/
@Mapper
public interface StationsMapper extends BaseMapper<Stations> {

}




