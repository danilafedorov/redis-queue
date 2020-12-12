package com.fedorov.example.redis.queue.config;

import com.fedorov.example.redis.queue.model.Event;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RSet;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AppConfig {

    private final RedissonClient redissonClient;

    private static final String QUEUE_NAME = "QueueSyncSet";

    @Bean
    public RSet<Event> getJournalForceQueue() {
        return redissonClient.getSet(QUEUE_NAME, new JsonJacksonCodec());
    }

}
