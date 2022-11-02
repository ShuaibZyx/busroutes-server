package com.shuaib.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shuaib.bean.UserAccount;
import com.shuaib.bean.UserInfo;
import org.apache.ibatis.annotations.*;


/**
 * @author shuai   b
 * @description 针对表【user_account】的数据库操作Mapper
 * @createDate 2022-11-01 14:39:59
 * @Entity com.shuaib.bean.UserAccount
 */
@Mapper
public interface UserAccountMapper extends BaseMapper<UserAccount> {

    @Select("select * from user_account")
    @Results({
            @Result(column = "user_id", property = "userId", id = true),
            @Result(column = "user_id", property = "userInfo", javaType = UserInfo.class,
                    one = @One(select = "com.shuaib.mapper.UserInfoMapper.selectById")
            )
    })
    IPage<UserAccount> getUserListPage(Page<UserAccount> page);
}




