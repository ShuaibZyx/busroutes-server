package com.shuaib.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuaib.bean.Buses;
import com.shuaib.common.Result;
import com.shuaib.service.BusesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/bus")
public class BusesController {

    @Autowired
    private BusesService busesService;

    /**
     * 分页获取所有公交车信息
     *
     * @param currentPage 当前页码
     * @param pageSize    页面大小
     * @return 通用返回格式
     */
    @GetMapping("/list")
    public Result getBusListPage(int currentPage, int pageSize) {
        return Result.success(busesService.getBaseMapper().selectPage(new Page<>(currentPage, pageSize), new QueryWrapper<Buses>().orderByDesc("create_time")).getRecords());
    }

    /**
     * 添加一个公交车信息
     * @param buses 公交车信息实体
     * @return  通用返回格式
     */
    @PostMapping("/create")
    public Result createBus(@RequestBody @Validated Buses buses) {
        busesService.save(buses);
        return Result.success("添加公交信息成功");
    }

    /**
     * 删除一个公交信息
     * @param busId 公交车信息编号
     * @return 通用返回格式
     */
    @DeleteMapping("/remove/{busId}")
    public Result removeBusById(@PathVariable("busId") Long busId) {
        busesService.removeById(busId);
        return Result.success("删除公交信息成功");
    }

    /**
     * 根据busId获取公交信息
     * @param busId 公交编号
     * @return 通用返回格式
     */
    @GetMapping("/info/{busId}")
    public Result getBusInfoById(@PathVariable("busId") Long busId) {
        return Result.success(busesService.getById(busId));
    }

    /**
     * 更新公交信息
     * @param bus 公交信息实体
     * @return 通用返回格式
     */
    @PostMapping("/update")
    public Result updateBusInfo(@RequestBody @Validated Buses bus){
        busesService.updateById(bus);
        return Result.success("更新公交信息成功");
    }

}
