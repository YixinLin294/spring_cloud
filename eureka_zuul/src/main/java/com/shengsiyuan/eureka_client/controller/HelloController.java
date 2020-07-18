package com.shengsiyuan.eureka_client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/hello")
    public String hello(String name) {
        return name + ", Welcome to Spring Boot 2" + "from port: " + port;
    }
}
