package com.shengsiyuan.eureka_client.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "rocketmq")
public class RocketMQProperties {

    private boolean isEnable;

    private String namesrvAddr;

    private String groupName;

    private int producerMaxMessageSize;

    private int producerSendMsgTimeout;

    private int producerRetryTimesWhenSendFailed;

    private int consumerConsumeThreadMin;

    private int consumerConsumeThreadMax;

    private int consumerConsumeMessageBatchMaxSize;

}
