package com.shuaib.mapper;

import com.shuaib.bean.RouteNodes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shuaib.bean.Stations;
import org.apache.ibatis.annotations.*;

import java.util.List;


/**
 * @author shuai   b
 * @description 针对表【route_nodes】的数据库操作Mapper
 * @createDate 2022-11-02 14:04:47
 * @Entity com.shuaib.bean.RouteNodes
 */
@Mapper
public interface RouteNodesMapper extends BaseMapper<RouteNodes> {

    /**
     * 根据rouNodeId获取一个节点信息(附加节点中站点信息)
     *
     * @param routeNodeId 节点编号
     * @return 节点对象
     */
    @Select("select * from route_nodes where route_node_id = #{routeNodeId} order by sequence asc")
    @Results({
            @Result(column = "station_id", property = "stationId"),
            @Result(column = "station_id", property = "currentStation", javaType = Stations.class,
                    one = @One(select = "com.shuaib.mapper.StationsMapper.selectById")
            ),
            @Result(column = "next_station_id", property = "nextStationId"),
            @Result(column = "next_station_id", property = "nextStation", javaType = Stations.class,
                    one = @One(select = "com.shuaib.mapper.StationsMapper.selectById")
            )
    })
    RouteNodes getRouteNodeInfoByRouteNodeId(Long routeNodeId);


    /**
     * 根据RouteId获取一个节点信息(附加节点中站点信息)
     *
     * @param routId 线路编号
     * @return 节点对象
     */
    @Select("select * from route_nodes where route_id = #{routId} order by sequence asc")
    @Results({
            @Result(column = "station_id", property = "stationId"),
            @Result(column = "station_id", property = "currentStation", javaType = Stations.class,
                    one = @One(select = "com.shuaib.mapper.StationsMapper.selectById")
            ),
            @Result(column = "next_station_id", property = "nextStationId"),
            @Result(column = "next_station_id", property = "nextStation", javaType = Stations.class,
                    one = @One(select = "com.shuaib.mapper.StationsMapper.selectById")
            )
    })
    List<RouteNodes> getRouteNodeListByRouteId(Long routId);

}




