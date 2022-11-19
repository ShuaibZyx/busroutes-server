package com.shuaib.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaib.bean.RouteNodes;
import com.shuaib.service.RouteNodesService;
import com.shuaib.mapper.RouteNodesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author shuai   b
 * @description 针对表【route_nodes】的数据库操作Service实现
 * @createDate 2022-11-02 14:04:47
 */
@Service
public class RouteNodesServiceImpl extends ServiceImpl<RouteNodesMapper, RouteNodes> implements RouteNodesService {
    @Autowired
    private RouteNodesMapper routeNodesMapper;

    /**
     * 获取单个节点信息
     *
     * @param routeNodeId 节点编号
     * @return 节点对象
     */
    @Override
    public RouteNodes getRouteNodeInfoById(Long routeNodeId) {
        return routeNodesMapper.getRouteNodeInfoByRouteNodeId(routeNodeId);
    }

    /**
     * 根据线路编号获取节点列表
     *
     * @param routId 线路编号
     * @return 节点列表
     */
    @Override
    public List<RouteNodes> getRouteNodeListByRouteId(Long routId) {
        return routeNodesMapper.getRouteNodeListByRouteId(routId);
    }
}




