package com.shuaib;

import com.shuaib.bean.Stations;
import com.shuaib.bean.UserAccount;
import com.shuaib.mapper.StationsMapper;
import com.shuaib.mapper.UserAccountMapper;
import com.shuaib.service.StationsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootApplicationTests {

    @Autowired
    private UserAccountMapper userAccountMapper;
    @Test
    void contextLoads() {
        UserAccount userAccount = new UserAccount();
        userAccount.setAccount("15024930879");
        userAccount.setAvailable(true);
        userAccount.setPassword("123456");
        userAccountMapper.insert(userAccount);
    }


}
