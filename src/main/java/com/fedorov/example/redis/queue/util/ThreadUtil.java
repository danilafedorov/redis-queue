package com.fedorov.example.redis.queue.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ThreadUtil {

    public void sleep(long sleepTimeOut) {
        try {
            Thread.sleep(sleepTimeOut);
        } catch (InterruptedException inEx) {
            throw new RuntimeException("Interrupted when sleep", inEx);
        }
    }
}
