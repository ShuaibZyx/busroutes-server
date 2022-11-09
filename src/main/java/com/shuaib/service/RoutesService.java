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

    /**
     * 根据编号获取线路详细信息,包括线路公交信息以及其包含的节点信息
     * @param routeId 线路编号
     * @return 线路对象
     */
    Routes getRouteInfoById(Long routeId);

    /**
     * 分页获取线路信息
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 线路列表
     */
    List<Routes> getRouteListPage(int currentPage, int pageSize);
}
