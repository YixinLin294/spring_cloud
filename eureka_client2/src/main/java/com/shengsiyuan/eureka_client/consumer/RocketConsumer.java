package com.shengsiyuan.eureka_client.consumer;

import org.apache.rocketmq.client.consumer.listener.MessageListener;

public interface RocketConsumer {

    void init();

    void registerMessageListener(MessageListener messageListener);
}
