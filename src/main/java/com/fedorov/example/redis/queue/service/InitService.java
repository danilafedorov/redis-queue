package com.fedorov.example.redis.queue.service;

import com.fedorov.example.redis.queue.service.thread.GetThread;
import com.fedorov.example.redis.queue.service.thread.PutThread;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InitService {

    private final ThreadPoolTaskExecutor executorGet = new ThreadPoolTaskExecutor();
    private final ThreadPoolTaskExecutor executorPut = new ThreadPoolTaskExecutor();

    private final QueueService queueService;
    private final Locker locker;

    public void start(int getThreadMax, int putThreadMax) {
        initPool(executorGet, getThreadMax, "GetThread-");
        initPool(executorPut, putThreadMax, "PutThread-");

        for (int i = 0; i < getThreadMax; i++) {
            executorGet.submit(new GetThread(queueService, locker));
        }

        for (int i = 0; i < putThreadMax; i++) {
            executorPut.submit(new PutThread(queueService));
        }
    }

    private void initPool(ThreadPoolTaskExecutor pool, int maxThreads, String threadNamePrefix) {
        if (maxThreads == 0) {
            return;
        }

        pool.setCorePoolSize(maxThreads);
        pool.setMaxPoolSize(maxThreads);
        pool.setQueueCapacity(2);
        pool.setThreadNamePrefix(threadNamePrefix);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        pool.initialize();
    }
}
