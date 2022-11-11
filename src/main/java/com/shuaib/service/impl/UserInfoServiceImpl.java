package com.shuaib.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaib.bean.UserInfo;
import com.shuaib.mapper.UserInfoMapper;
import com.shuaib.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 通过编号查询用户附加信息
     *
     * @param userId 用户编号
     * @return 通用返回格式
     */
    @Override
    public UserInfo getUserInfoById(Long userId) {
        return userInfoMapper.getUserInfoById(userId);
    }
}
