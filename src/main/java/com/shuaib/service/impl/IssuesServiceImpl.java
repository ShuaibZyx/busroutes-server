package com.shuaib.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaib.bean.Issues;
import com.shuaib.service.IssuesService;
import com.shuaib.mapper.IssuesMapper;
import org.springframework.stereotype.Service;

/**
* @author shuai   b
* @description 针对表【issues】的数据库操作Service实现
* @createDate 2022-11-02 14:04:47
*/
@Service
public class IssuesServiceImpl extends ServiceImpl<IssuesMapper, Issues>
    implements IssuesService{

}




