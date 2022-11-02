package com.shuaib.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shuaib.bean.UserAccount;
import com.shuaib.mapper.UserAccountMapper;
import com.shuaib.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

    @Autowired
    private UserAccountMapper userAccountMapper;

    /**
     * 分页获取用户列表信息
     * @param currentPage 当前页码
     * @param pageSize 当前页面大小
     * @return 用户列表信息
     */
    @Override
    public List<UserAccount> getUserListPage(int currentPage, int pageSize) {
        return userAccountMapper.getUserListPage(new Page<>(currentPage, pageSize)).getRecords();
    }
}
