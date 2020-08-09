package com.shengsiyuan.eureka_client.controller;

import com.shengsiyuan.eureka_client.service.ProducerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProducerController {

    private ProducerService producerService;

    @GetMapping("/push")
    public void pushMsg(@RequestParam String msg, @RequestParam String topic, @RequestParam Integer times) {
        for (int i = 0; i < times; i++) {
            producerService.send(topic, "push", msg + i);
        }
    }
}
