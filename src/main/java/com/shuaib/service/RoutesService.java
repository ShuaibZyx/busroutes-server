package com.shuaib.service;

import com.shuaib.bean.Routes;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author shuai   b
* @description 针对表【routes】的数据库操作Service
* @createDate 2022-11-02 14:04:47
*/
public interface RoutesService extends IService<Routes> {

    Routes getRouteInfoById(Long routeId);
}
