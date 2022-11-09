package com.shuaib.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shuaib.bean.Routes;
import com.shuaib.common.Result;
import com.shuaib.service.RoutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
        return Result.success(routesService.getRouteListPage(currentPage, pageSize));
    }

    /**
     * 获取线路总数量
     * @return 通用返回格式
     */
    @GetMapping("/count")
    public Result getRouteCount() {
        return Result.success(routesService.count());
    }

    /**
     * 更新路线基本信息
     *
     * @param routes 路线实体对象
     * @return 通用返回格式
     */
    @PostMapping("/modify/baseInfo")
    public Result updateRouteBaseInfoById(@RequestBody @Validated Routes routes) {
        UpdateWrapper<Routes> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("bus_id", routes.getBusId());
        updateWrapper.set("cost", routes.getCost());
        updateWrapper.set("time_range", routes.getTimeRange());
        updateWrapper.set("bus_interval", routes.getBusInterval());
        updateWrapper.set("is_night", routes.getIsNight());
        updateWrapper.set("is_circle", routes.getIsCircle());
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
     * 根据编号删除一条线路
     * @param routeId 线路编号
     * @return 通用返回格式
     */
    @DeleteMapping("/remove/{routeId}")
    public Result removeRouteById(@PathVariable("routeId") Long routeId) {
        routesService.removeById(routeId);
        return Result.success("该线路已被删除");
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
