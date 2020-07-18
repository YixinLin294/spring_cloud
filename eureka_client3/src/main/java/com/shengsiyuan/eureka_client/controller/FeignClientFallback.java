package com.shengsiyuan.eureka_client.controller;

import org.springframework.stereotype.Component;

@Component
public class FeignClientFallback implements EurekaFeignClient {
    @Override
    public String hello(String name) {
        return "error";
    }
}
