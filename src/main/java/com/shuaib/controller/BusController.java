package com.shuaib.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuaib.bean.Bus;
import com.shuaib.common.Result;
import com.shuaib.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bus")
public class BusController {

    @Autowired
    private BusService busService;

    /**
     * 分页获取所有公交车信息
     *
     * @param currentPage 当前页码
     * @param pageSize    页面大小
     * @return 通用返回格式
     */
    @GetMapping("/list")
    public Result getBusListPage(int currentPage, int pageSize) {
        return Result.success(busService.getBaseMapper().selectPage(new Page<>(currentPage, pageSize), new QueryWrapper<>()).getRecords());
    }

    /**
     * 添加一个公交车信息
     * @param bus 公交车信息实体
     * @return  通用返回格式
     */
    @PostMapping("/create")
    public Result createBus(@RequestBody Bus bus) {
        busService.save(bus);
        return Result.success("添加公交信息成功");
    }

    /**
     * 删除一个公交信息
     * @param busId 公交车信息编号
     * @return 通用返回格式
     */
    @DeleteMapping("/remove/{busId}")
    public Result removeBus(@PathVariable("busId") Long busId) {
        busService.removeById(busId);
        return Result.success("删除公交信息成功");
    }

    /**
     * 根据busId获取公交信息
     * @param busId 公交编号
     * @return 通用返回格式
     */
    @GetMapping("/info/{busId}")
    public Result getBusInfoById(@PathVariable("busId") Long busId) {
        return Result.success(busService.getById(busId));
    }

    /**
     * 修改公交信息
     * @param bus 公交信息实体
     * @return 通用返回格式
     */
    @PostMapping("/update")
    public Result updateBusInfo(@RequestBody Bus bus){
        busService.updateById(bus);
        return Result.success("修改公交信息成功");
    }

}
