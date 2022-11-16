package com.shuaib.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuaib.bean.Buses;
import com.shuaib.bean.RouteNodes;
import com.shuaib.bean.Routes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

import static net.sf.jsqlparser.parser.feature.Feature.select;
import static net.sf.jsqlparser.util.validation.metadata.NamedObject.column;

/**
 * @author shuai   b
 * @description 针对表【routes】的数据库操作Mapper
 * @createDate 2022-11-02 14:04:47
 * @Entity com.shuaib.bean.Routes
 */
@Mapper
public interface RoutesMapper extends BaseMapper<Routes> {

    /**
     * 获取某个线路详细信息(线路信息,公交信息,线路包含的节点信息,节点包含的站点信息)
     *
     * @param routeId 路线编号
     * @return 线路对象
     */
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

    /**
     * 分页获得线路列表(包含公交信息)
     * @param page 分页配置对象
     * @return 分页信息
     */
    @Select("select * from routes order by create_time desc")
    @Results({
            @Result(column = "bus_id", property = "busId"),
            @Result(column = "bus_id", property = "bus", javaType = Buses.class,
                    one = @One(select = "com.shuaib.mapper.BusesMapper.selectById"))
    })
    IPage<Routes> getRouteListPage(Page<Routes> page);
}




