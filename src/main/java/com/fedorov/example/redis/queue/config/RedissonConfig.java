package com.fedorov.example.redis.queue.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

    @Value("${redis.url}")
    private String url;

    @Value("${redis.database}")
    private Integer database;

    @Value("${redis.connection-pool-size}")
    private int connectionPoolSize;


    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress(url)
                .setConnectionPoolSize(connectionPoolSize)
                .setDatabase(database);
        return Redisson.create(config);
    }
}
