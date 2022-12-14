package com.shuaib.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaib.bean.Stations;
import com.shuaib.service.StationsService;
import com.shuaib.mapper.StationsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author shuai   b
* @description 针对表【stations】的数据库操作Service实现
* @createDate 2022-11-02 14:04:47
*/
@Service
public class StationsServiceImpl extends ServiceImpl<StationsMapper, Stations>
    implements StationsService{

}




