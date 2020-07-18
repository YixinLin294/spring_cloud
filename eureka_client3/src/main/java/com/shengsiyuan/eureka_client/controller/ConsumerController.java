package com.shengsiyuan.eureka_client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    @Autowired
    RestTemplate restTemplate;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Autowired
    EurekaFeignClient eurekaFeignClient;

    @GetMapping("/hi")
    public String hello(String name) {
//        ServiceInstance choose = loadBalancerClient.choose("eureka-client2");
//        int port = choose.getPort();
//        System.out.println(port);
        return restTemplate.getForObject("http://eureka-client2/hello?name=" + name, String.class);
    }

    @GetMapping("/hi_feign")
    public String helloFeign(String name) {
        return eurekaFeignClient.hello(name);
    }
}
