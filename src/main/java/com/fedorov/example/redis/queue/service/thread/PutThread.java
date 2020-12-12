package com.fedorov.example.redis.queue.service.thread;

import com.fedorov.example.redis.queue.model.Event;
import com.fedorov.example.redis.queue.service.QueueService;
import com.fedorov.example.redis.queue.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;

@Slf4j
@RequiredArgsConstructor
public class PutThread implements Runnable {

    private final QueueService queueService;

    @Override
    public void run() {
        try {
            while (true) {
                String string = RandomStringUtils.random(9, true, true);

                boolean result = queueService.put(new Event(string));

                log.info("Put element: result {}, element: {}", result, string);

                ThreadUtil.sleep(30);
            }
        } catch (Exception ex) {
            log.error("Unexpected excception ", ex);
        }
    }
}
