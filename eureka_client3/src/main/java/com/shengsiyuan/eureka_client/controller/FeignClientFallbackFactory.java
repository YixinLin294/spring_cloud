package com.shengsiyuan.eureka_client.controller;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class FeignClientFallbackFactory implements FallbackFactory<EurekaFeignClient> {
    @Override
    public EurekaFeignClient create(Throwable cause) {
        return new EurekaFeignClient() {
            @Override
            public String hello(String name) {
                return "get trigger hystrix open ! reason:" + cause.getMessage();
            }
        };
    }
}
