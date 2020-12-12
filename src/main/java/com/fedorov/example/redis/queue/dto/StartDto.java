package com.fedorov.example.redis.queue.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StartDto {
    private int getThreadMax;
    private int putThreadMax;
}
