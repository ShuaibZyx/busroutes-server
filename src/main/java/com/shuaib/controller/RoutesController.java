package com.shuaib.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuaib.bean.Routes;
import com.shuaib.common.Result;
import com.shuaib.service.RoutesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public Result createRoute(@RequestBody Routes routes) {
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
     * @param routes 路线实体对象
     * @return 通用返回格式
     */
    @PostMapping("/update")
    public Result updateRouteBaseInfoById(@RequestBody Routes routes) {
        UpdateWrapper<Routes> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("cost", routes.getCost());
        updateWrapper.set("time_range", routes.getTimeRange());
        updateWrapper.set("interval", routes.getInterval());
        updateWrapper.eq("route_id", routes.getRouteId());
        routesService.update(updateWrapper);
        return Result.success("更新路线基本信息成功");
    }

    @GetMapping("/info/{routeId}")
    public Result getRouteInfoById(@PathVariable("routeId") Long routeId){
        return null;
    }
}
