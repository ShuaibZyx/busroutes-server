package com.shuaib.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shuaib.bean.UserAccount;
import java.util.List;

/**
 * @author shuai   b
 * @description 针对表【user_account】的数据库操作Service
 * @createDate 2022-11-02 14:04:47
 */

public interface UserAccountService extends IService<UserAccount> {
    /**
     * 分页获取用户列表信息
     * @param currentPage 当前页码
     * @param pageSize 当前页面大小
     * @return 用户列表信息
     */
    List<UserAccount> getUserListPage (int currentPage, int pageSize);
}
