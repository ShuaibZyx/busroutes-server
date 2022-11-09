package com.shuaib.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaib.bean.Routes;
import com.shuaib.service.RoutesService;
import com.shuaib.mapper.RoutesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @author shuai   b
 * @description 针对表【routes】的数据库操作Service实现
 * @createDate 2022-11-02 14:04:47
 */
@Service
public class RoutesServiceImpl extends ServiceImpl<RoutesMapper, Routes> implements RoutesService {
    @Autowired
    private RoutesMapper routesMapper;

    /**
     * 根据编号获取线路详细信息
     *
     * @param routeId 线路编号
     * @return 线路对象
     */
    @Override
    public Routes getRouteInfoById(Long routeId) {
        return routesMapper.getRouteInfoById(routeId);
    }

    /**
     * 分页获取线路列表
     *
     * @param currentPage 当前页
     * @param pageSize    页面大小
     * @return 线路列表
     */
    @Override
    public List<Routes> getRouteListPage(int currentPage, int pageSize) {
        return routesMapper.getRouteListPage(new Page<>(currentPage, pageSize)).getRecords();
    }
}




