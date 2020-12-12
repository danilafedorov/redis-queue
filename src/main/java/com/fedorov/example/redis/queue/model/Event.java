package com.fedorov.example.redis.queue.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Event {
    private String number;

    public Event(String number) {
        this.number = number;
    }
}
