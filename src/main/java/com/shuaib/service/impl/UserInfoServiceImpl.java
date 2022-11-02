package com.shuaib.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaib.bean.UserInfo;
import com.shuaib.mapper.UserInfoMapper;
import com.shuaib.service.UserInfoService;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
}
