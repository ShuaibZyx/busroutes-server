package com.shuaib;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;

@org.springframework.boot.autoconfigure.SpringBootApplication
@MapperScan("com.shuaib.mapper")
public class SpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }

}
