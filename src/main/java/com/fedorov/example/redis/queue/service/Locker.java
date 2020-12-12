package com.fedorov.example.redis.queue.service;

import com.sun.istack.internal.NotNull;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class Locker {

    private static String prefix = "RedisLockTest.";

    private final RedissonClient redissonClient;

    @NotNull
    public Collection<String> lock(Collection<String> elements) {
        Collection<String> success = new ArrayList<>();

        elements.forEach(value -> {
              RLock rLock = redissonClient.getLock(prefix + value);

              if (rLock.tryLock()) {
                  success.add(value);
              }
        });
        return success;
    }

    public void unLock(Collection<String> elements) {
        elements.forEach(value -> {
            RLock rLock = redissonClient.getLock(prefix + value);
            rLock.unlock();
        });
    }
}
