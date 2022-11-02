package com.shuaib.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaib.bean.Bus;
import com.shuaib.service.BusService;
import com.shuaib.mapper.BusMapper;
import org.springframework.stereotype.Service;

/**
* @author shuai   b
* @description 针对表【bus】的数据库操作Service实现
* @createDate 2022-11-02 14:04:47
*/
@Service
public class BusServiceImpl extends ServiceImpl<BusMapper, Bus>
    implements BusService{

}




