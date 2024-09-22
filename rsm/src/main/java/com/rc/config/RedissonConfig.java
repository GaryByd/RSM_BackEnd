package com.rc.config;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {//本项目没有用到分布式锁 可以把这个文件删了

    @Bean
    public RedissonClient redissonClient() {
        //配置
        Config config = new Config();
//        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setPassword("123456").setDatabase(0);
        config.useSingleServer().setAddress("redis://111.230.32.147:9631").setDatabase(3).setPassword("4+K/D2zvxHooBhn5begcMQz/04IpAMpq");
        ;
        //创建对象
        return Redisson.create(config);
    }
}
