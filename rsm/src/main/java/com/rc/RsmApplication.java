package com.rc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScan("com.rc.mapper")
@SpringBootApplication
public class RsmApplication {
    public static void main(String[] args) {
        SpringApplication.run(RsmApplication.class, args);
    }
}
