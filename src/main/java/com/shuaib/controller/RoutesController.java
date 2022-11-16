package com.shuaib.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.shuaib.bean.RouteNodes;
import com.shuaib.bean.Routes;
import com.shuaib.bean.Stations;
import com.shuaib.common.Result;
import com.shuaib.service.RouteNodesService;
import com.shuaib.service.RoutesService;
import com.shuaib.service.StationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/route")
public class RoutesController {

    @Autowired
    private RoutesService routesService;

    @Autowired
    private StationsService stationsService;

    @Autowired
    private RouteNodesService routeNodesService;

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
     *
     * @return 通用返回格式
     */
    @GetMapping("/count")
    public Result getRouteCount() {
        return Result.success(routesService.count());
    }

    /**
     * 更新路线基本信息
     *
     * @param route 路线实体对象
     * @return 通用返回格式
     */
    @PostMapping("/modify/baseInfo")
    public Result updateRouteBaseInfoById(@RequestBody @Validated Routes route) {
        routesService.updateById(route);
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
        Routes route = routesService.getRouteInfoById(routeId);
        route.getRouteNodeList().sort(Comparator.comparingInt(RouteNodes::getSequence));
        return Result.success(route);
    }


    /**
     * 根据编号删除一条线路
     *
     * @param routeId 线路编号
     * @return 通用返回格式
     */
    @DeleteMapping("/remove/{routeId}")
    public Result removeRouteById(@PathVariable("routeId") Long routeId) {
        routesService.removeById(routeId);
        return Result.success("该线路已被删除");
    }

    /**
     * 修改线路的起始次序
     *
     * @param routeId       线路编号
     * @param startSequence 起始次序
     * @return 通用返回格式
     */
    @PutMapping("/modify/startSequence/{routeId}")
    public Result modifyRouteStartSequence(@PathVariable("routeId") Long routeId, @RequestParam Integer startSequence) {
        UpdateWrapper<Routes> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("start_sequence", startSequence).eq("route_id", routeId);
        routesService.update(updateWrapper);
        return Result.success("已设置该线路起始次序为" + startSequence);
    }

    /**
     * 修改线路的终止次序
     *
     * @param routeId     线路编号
     * @param endSequence 终止次序
     * @return 通用返回格式
     */
    @PutMapping("/modify/endSequence/{routeId}")
    public Result modifyRouteEndSequence(@PathVariable("routeId") Long routeId, @RequestParam Integer endSequence) {
        UpdateWrapper<Routes> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("end_sequence", endSequence).eq("route_id", routeId);
        routesService.update(updateWrapper);
        return Result.success("已设置该线路终止次序为" + endSequence);
    }

    /**
     * 根据用户所在城市模糊搜索公交线路列表
     *
     * @param key      用户输入的关键字
     * @param cityCode 用户所在城市数字字符串
     * @return 通用返回格式
     */
    @GetMapping("/search/{key}")
    public Result searchRouteListByKey(@PathVariable("key") String key, @RequestParam String cityCode) {
        //根据关键词从数据库中查询符合的站点
        List<Stations> stationList = stationsService.getBaseMapper().selectList(new QueryWrapper<Stations>().select("station_id").eq("city_code", cityCode).like("station_name", key));
        //将站点集合转为站点编号集合
        List<Long> stationIdList = stationList.stream().map(Stations::getStationId).collect(Collectors.toList());
        //根据站点编号集合查询符合的节点列表
        List<RouteNodes> routeNodeList = routeNodesService.getBaseMapper().selectList(new QueryWrapper<RouteNodes>().select("route_id").in("station_id", stationIdList));
        //将符合节点列表转为线路编号列表并去重
        List<Long> routeIdList = routeNodeList.stream().map(RouteNodes::getRouteId).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());
        //线路列表对象
        List<Routes> routeList = new ArrayList<>();
        //根据线路编号装载线路列表
        for (Long routeId : routeIdList) routeList.add(routesService.getRouteInfoById(routeId));
        return Result.success(routeList);
    }

    /**
     * 根据起始地址和结束地址查询线路
     *
     * @param start    起始地址关键词
     * @param end      结束地址关键词
     * @param cityCode 用户所在城市
     * @return 通用返回格式
     */
    @GetMapping("/search/section")
    public Result searchRouteListSection(String start, String end, String cityCode) {
        //根据目的地关键词从数据库中查询符合的站点
        List<Stations> stationList = stationsService.getBaseMapper().selectList(new QueryWrapper<Stations>().select("station_id").eq("city_code", cityCode).like("station_name", end));
        //将站点集合转为站点编号集合
        List<Long> stationIdList = stationList.stream().map(Stations::getStationId).collect(Collectors.toList());
        //根据站点编号集合查询符合的节点列表
        List<RouteNodes> routeNodeList = routeNodesService.getBaseMapper().selectList(new QueryWrapper<RouteNodes>().select("route_id", "sequence").in("station_id", stationIdList));
        //将符合的节点列表转为线路编号列表并去重
        List<Long> routeIdList = routeNodeList.stream().map(RouteNodes::getRouteId).collect(Collectors.toList()).stream().distinct().collect(Collectors.toList());
        //线路列表对象
        List<Routes> routeList = new ArrayList<>();
        //根据线路编号装载线路列表
        for (Long routeId : routeIdList) routeList.add(routesService.getRouteInfoById(routeId));
        routeList = routeList.stream().filter((Routes route) -> route.getRouteNodeList().get(route.getStartSequence()).getCurrentStation().getStationName().contains(start)).collect(Collectors.toList());
        return Result.success(routeList);
    }
}
