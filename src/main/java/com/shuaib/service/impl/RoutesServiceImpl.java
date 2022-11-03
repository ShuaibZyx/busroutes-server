package com.shuaib.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaib.bean.Routes;
import com.shuaib.service.RoutesService;
import com.shuaib.mapper.RoutesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author shuai   b
 * @description 针对表【routes】的数据库操作Service实现
 * @createDate 2022-11-02 14:04:47
 */
@Service
public class RoutesServiceImpl extends ServiceImpl<RoutesMapper, Routes> implements RoutesService {
    @Autowired
    private RoutesMapper routesMapper;

    @Override
    public Routes getRouteInfoById(Long routeId) {
        return routesMapper.getRouteInfoById(routeId);
    }
}




