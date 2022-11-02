package com.shuaib.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaib.bean.Messages;
import com.shuaib.service.MessagesService;
import com.shuaib.mapper.MessagesMapper;
import org.springframework.stereotype.Service;

/**
* @author shuai   b
* @description 针对表【messages】的数据库操作Service实现
* @createDate 2022-11-02 14:04:47
*/
@Service
public class MessagesServiceImpl extends ServiceImpl<MessagesMapper, Messages>
    implements MessagesService{

}




