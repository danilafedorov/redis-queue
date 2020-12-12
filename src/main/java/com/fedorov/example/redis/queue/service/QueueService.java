package com.fedorov.example.redis.queue.service;

import com.fedorov.example.redis.queue.model.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;


@Slf4j
@Service
@RequiredArgsConstructor
public class QueueService {

    private static final int GET_LIMIT = 10;

    @Value("${queue.size}")
    private int queueSize;


    private final RSet<Event> mainQueue;

    public Collection<Event> get() {

        try {
            Collection<Event> events = mainQueue.removeRandom(GET_LIMIT);
            if (events == null) {
                return Collections.emptyList();
            } else {
                return events;
            }
        } catch (Exception ex) {
            log.error("get. Unexpected exception ", ex);
            return Collections.emptyList();
        }

    }

    public boolean put(Event element) {

        try {
            // You must remember: This does not guarantee that the queue size will not exceed the maximum size.
            // but for us it was doesn't matter :)
            if (mainQueue.size() < queueSize) {

                boolean result = mainQueue.add(element);
                int size = mainQueue.size();
                if (size > queueSize) {
                    log.error("queue is full {}", size);
                }
                return result;
            } else {
                return false;
            }
        } catch (Exception ex) {
            log.error("add. Unexpected exception ", ex);
            return false;
        }
    }
}
