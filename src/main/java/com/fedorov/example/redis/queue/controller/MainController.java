package com.fedorov.example.redis.queue.controller;

import com.fedorov.example.redis.queue.dto.StartDto;
import com.fedorov.example.redis.queue.service.InitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final InitService initService;

    @PostMapping(value = "/start")
    public void start(@RequestBody StartDto startDto) {
        initService.start(startDto.getGetThreadMax(), startDto.getPutThreadMax());
    }
}
