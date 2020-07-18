package com.shengsiyuan.eureka_client.controller;

import com.shengsiyuan.eureka_client.configuration.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "eureka-client2", configuration = FeignConfig.class, fallbackFactory = FeignClientFallbackFactory.class)
public interface EurekaFeignClient {

    @GetMapping("/hello")
    String hello(@RequestParam String name);
}
