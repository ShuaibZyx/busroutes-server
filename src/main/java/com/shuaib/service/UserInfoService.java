package com.shuaib.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuaib.bean.UserInfo;

/**
 * @author shuai   b
 * @description 针对表【user_info】的数据库操作Service
 * @createDate 2022-11-02 14:04:47
 */
public interface UserInfoService extends IService<UserInfo> {
    /**
     * 通过编号查询用户附加信息
     * @param userId 用户编号
     * @return 通用返回格式
     */
    UserInfo getUserInfoById(Long userId);
}
