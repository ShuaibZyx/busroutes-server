package com.shuaib.mapper;

import com.shuaib.bean.Buses;
import com.shuaib.bean.RouteNodes;
import com.shuaib.bean.Routes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.column;

/**
 * @author shuai   b
 * @description 针对表【routes】的数据库操作Mapper
 * @createDate 2022-11-02 14:04:47
 * @Entity com.shuaib.bean.Routes
 */
@Mapper
public interface RoutesMapper extends BaseMapper<Routes> {


    @Select("select * from routes where route_id = #{routeId}")
    @Results({
            @Result(column = "route_id", property = "routeId", id = true),
            @Result(column = "route_id", property = "routeNodeList", javaType = List.class,
                    many = @Many(select = "com.shuaib.mapper.RouteNodesMapper.getRouteNodeInfoByRouteId")),
            @Result(column = "bus_id", property = "busId"),
            @Result(column = "bus_id", property = "bus", javaType = Buses.class,
                    one = @One(select = "com.shuaib.mapper.BusesMapper.selectById"))
    })
    Routes getRouteInfoById(Long routeId);


}




