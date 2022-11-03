package com.shuaib.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuaib.bean.Stations;
import com.shuaib.common.Result;
import com.shuaib.service.StationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/station")
public class StationsController {
    @Autowired
    private StationsService stationsService;

    /**
     * 分页获取站点信息列表
     * @param currentPage 当前页码
     * @param pageSize 页面大小
     * @return 通用返回格式
     */
    @GetMapping("/list")
    public Result getStationListPage(int currentPage, int pageSize){
        return Result.success(stationsService.getBaseMapper().selectPage(new Page<>(currentPage,pageSize),new QueryWrapper<Stations>().orderByDesc("create_time")).getRecords());
    }

    /**
     * 根据Id获取站点
     * @param stationId 站点编号
     * @return 通用返回格式
     */
    @GetMapping("/info")
    public Result getStationById(Long stationId){
        return Result.success(stationsService.getById(stationId));
    }
    /**
     * 添加一个站点
     * @param stations 站点对象实体，包含站点信息
     * @return 通用返回格式
     */
    @PostMapping("/create")
    public Result createStation(@RequestBody Stations stations){
        stationsService.save(stations);
        return Result.success("添加站点成功");
    }

    /**
     * 删除站点信息
     * @param stationId 站点编号
     * @return 用返回格式
     */
    @DeleteMapping("/remove")
    public Result removeStation(Long stationId){
        stationsService.removeById(stationId);
        return Result.success("删除站点成功");
    }

    /**
     * 更新站点信息
     * @param stations 站点对象实体，包含站点信息
     * @return 通用返回格式
     */
    @PostMapping("/update")
    public Result updateStation(@RequestBody Stations stations){
        stationsService.updateById(stations);
        return Result.success("站点信息更新成功");
    }

}
