package com.shuaib.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuaib.bean.Routes;
import com.shuaib.common.Result;
import com.shuaib.service.RoutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping("/route")
public class RoutesController {

    @Autowired
    private RoutesService routesService;

    /**
     * 创建一条线路
     *
     * @param routes 线路实体对象
     * @return 通用返回格式
     */
    @PostMapping("/create")
    public Result createRoute(@RequestBody @Validated Routes routes) {
        routesService.save(routes);
        return Result.success("创建线路成功");
    }

    /**
     * 分页获取所有路线
     *
     * @param currentPage 当前页码
     * @param pageSize    页面大小
     * @return 通用返回格式
     */
    @GetMapping("/list")
    public Result getRouteListPage(int currentPage, int pageSize) {
        Page<Routes> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Routes> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        List<Routes> routeList = routesService.getBaseMapper().selectPage(page, queryWrapper).getRecords();
        return Result.success(routeList);
    }

    /**
     * 更新路线基本信息
     *
     * @param routes 路线实体对象
     * @return 通用返回格式
     */
    @PostMapping("/update/baseInfo")
    public Result updateRouteBaseInfoById(@RequestBody @Validated Routes routes) {
        UpdateWrapper<Routes> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("cost", routes.getCost());
        updateWrapper.set("time_range", routes.getTimeRange());
        updateWrapper.set("interval", routes.getInterval());
        updateWrapper.eq("route_id", routes.getRouteId());
        routesService.update(updateWrapper);
        return Result.success("更新路线基本信息成功");
    }

    /**
     * 获取线路的详细信息
     *
     * @param routeId 线路编号
     * @return 通用返回格式
     */
    @GetMapping("/info/{routeId}")
    public Result getRouteInfoById(@PathVariable("routeId") Long routeId) {
        return Result.success(routesService.getRouteInfoById(routeId));
    }

    /**
     * 修改线路夜间状态
     *
     * @param routeId 线路编号
     * @param isNight 是否为夜间路线
     * @return 通用返回格式
     */
    @PutMapping("/update/night")
    public Result setRouteNight(@NotNull @NotEmpty Long routeId, Boolean isNight) {
        UpdateWrapper<Routes> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_night", isNight).eq("route_id", routeId);
        routesService.update(updateWrapper);
        return Result.success("夜间线路状态设置成功");
    }

    /**
     * 修改线路环线状态
     *
     * @param routeId  线路编号
     * @param isCircle 是否为环形路线
     * @return 通用返回格式
     */
    @PutMapping("/update/circle")
    public Result setRouteCircle(@NotNull @NotEmpty Long routeId, Boolean isCircle) {
        UpdateWrapper<Routes> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_circle", isCircle).eq("route_id", routeId);
        routesService.update(updateWrapper);
        return Result.success("环形线路状态设置成功");
    }

    /**
     * 修改线路始末节点
     *
     * @param routeId       线路编号
     * @param startSequence 开始次序
     * @param endSequence   结束次序
     * @return 通用返回格式
     */
    @PutMapping("/update/sequence")
    public Result updateRouteSequence(@NotNull @NotEmpty Long routeId, Integer startSequence, Integer endSequence) {
        UpdateWrapper<Routes> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("start_sequence", startSequence).set("end_sequence", endSequence).eq("route_id", routeId);
        routesService.update(updateWrapper);
        return Result.success("线路始末节点修改成功");
    }
}
