package com.shuaib;

import com.shuaib.bean.UserAccount;
import com.shuaib.mapper.UserAccountMapper;
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
        userAccount.setAvailable(1);
        userAccount.setPassword("123456");
        userAccountMapper.insert(userAccount);
    }

}
