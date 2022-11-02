package com.shuaib.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaib.bean.Notices;
import com.shuaib.service.NoticesService;
import com.shuaib.mapper.NoticesMapper;
import org.springframework.stereotype.Service;

/**
* @author shuai   b
* @description 针对表【notices】的数据库操作Service实现
* @createDate 2022-11-02 14:04:47
*/
@Service
public class NoticesServiceImpl extends ServiceImpl<NoticesMapper, Notices>
    implements NoticesService{

}




