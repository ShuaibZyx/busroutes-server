package com.shuaib.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaib.bean.Buses;
import com.shuaib.service.BusesService;
import com.shuaib.mapper.BusesMapper;
import org.springframework.stereotype.Service;

/**
* @author shuai   b
* @description 针对表【buses】的数据库操作Service实现
* @createDate 2022-11-02 14:04:47
*/
@Service
public class BusesServiceImpl extends ServiceImpl<BusesMapper, Buses>
    implements BusesService {

}




