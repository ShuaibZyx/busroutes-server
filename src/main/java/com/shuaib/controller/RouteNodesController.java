package com.shuaib.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shuaib.bean.RouteNodes;
import com.shuaib.common.Result;
import com.shuaib.service.RouteNodesService;
import com.shuaib.service.RoutesService;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/routeNode")
public class RouteNodesController {
    @Autowired
    private RouteNodesService routeNodesService;

    /**
     * 获取单个节点详细信息
     *
     * @param routeNodeId 节点编号
     * @return 通用返回格式
     */
    @GetMapping("/info/{routeNodeId}")
    public Result getRouteNodeInfoById(@PathVariable("routeNodeId") Long routeNodeId) {
        return Result.success(routeNodesService.getRouteNodeInfoById(routeNodeId));
    }

    /**
     * 添加一个节点
     *
     * @param routeNodes 节点对象
     * @return 通用返回格式
     */
    @Transactional
    @PostMapping("/create")
    public Result createRouteNode(@RequestBody @Validated RouteNodes routeNodes) {
        routeNodesService.save(routeNodes);
        return Result.success("添加节点成功");
    }

    /**
     * 删除一个节点
     *
     * @param routeNodeId 节点编号
     * @return 通用返回格式
     */
    @DeleteMapping("/remove/{routeNodeId}")
    public Result deleteRouteNode(@PathVariable("routeNodeId") Long routeNodeId) {
        routeNodesService.removeById(routeNodeId);
        return Result.success("删除节点成功");
    }

    /**
     * 修改节点距离
     *
     * @param routeNodeId 节点编号
     * @param distance    距离(单位:米)
     * @return 通用返回格式
     */
    @PutMapping("/modify/distance")
    public Result updateRouteNodeDistance(@NotNull Long routeNodeId,
                                          @Range(min = 100, max = 5000, message = "站点之间距离应在100~5000米之间") Double distance) {
        UpdateWrapper<RouteNodes> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("distance", distance).eq("route_node_id", routeNodeId);
        routeNodesService.update(updateWrapper);
        return Result.success("节点距离修改成功");
    }

    /**
     * 修改节点相对次序
     *
     * @param routeNodeId 节点编号
     * @param sequence    相对次序
     * @return 通用返回格式
     */
    @PutMapping("/modify/sequence")
    public Result updateRouteNodeSequence(@NotNull Long routeNodeId, Integer sequence) {
        UpdateWrapper<RouteNodes> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("sequence", sequence).eq("route_node_id", routeNodeId);
        routeNodesService.update(updateWrapper);
        return Result.success("节点次序修改成功");
    }
}
