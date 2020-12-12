package com.fedorov.example.redis.queue.service.thread;

import com.fedorov.example.redis.queue.model.Event;
import com.fedorov.example.redis.queue.service.Locker;
import com.fedorov.example.redis.queue.service.QueueService;
import com.fedorov.example.redis.queue.util.ThreadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * This worker get some data from queue, do some work (with lock)
 */
@Slf4j
@RequiredArgsConstructor
public class GetThread implements Runnable {

    private final QueueService queueService;

    private final Locker locker;

    @Override
    public void run() {

        Collection<Event> elements;

        try {
            while (true) {

                elements = queueService.get();

                Collection<String> lockedElements;

                if (!elements.isEmpty()) {

                    Collection<String> numbers = elements.stream()
                                                         .map(Event::getNumber)
                                                         .collect(Collectors.toList());

                    lockedElements = locker.lock(numbers);

                    // do some work

                    locker.unLock(lockedElements);

                    log.info("Success processed elements: {}", lockedElements);

                } else {
                    log.info("No elements in queue");
                    ThreadUtil.sleep(2000);
                }
            }
        } catch (Exception ex) {

            log.error("Unexpected exception ", ex);
        }
    }


}
