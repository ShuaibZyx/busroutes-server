package com.shuaib.service;

import com.shuaib.bean.RouteNodes;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


/**
 * @author shuai   b
 * @description 针对表【route_nodes】的数据库操作Service
 * @createDate 2022-11-02 14:04:47
 */
public interface RouteNodesService extends IService<RouteNodes> {

    RouteNodes getRouteNodeInfoById(Long routeNodeId);

    List<RouteNodes> getRouteNodeListByRouteId(Long routId);
}
