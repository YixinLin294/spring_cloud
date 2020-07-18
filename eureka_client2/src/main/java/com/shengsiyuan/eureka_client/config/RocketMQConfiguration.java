package com.shengsiyuan.eureka_client.config;

import com.shengsiyuan.eureka_client.consumer.AbstractRocketConsumer;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({RocketMQProperties.class})
@ConditionalOnProperty(prefix = "rocketmq", value = "isEnable", havingValue = "true")
public class RocketMQConfiguration {

    private RocketMQProperties properties;

    private ApplicationContext applicationContext;

    private Logger log = LoggerFactory.getLogger(RocketMQConfiguration.class);

    public RocketMQConfiguration(RocketMQProperties properties, ApplicationContext applicationContext) {
        this.properties = properties;
        this.applicationContext = applicationContext;
    }

    @Bean
    public DefaultMQProducer getRocketMQProducer() throws MQClientException {
        if (StringUtils.isEmpty(properties.getGroupName())) {
            throw new MQClientException(-1, "groupName is blank");
        }

        if (StringUtils.isEmpty(properties.getNamesrvAddr())) {
            throw new MQClientException(-1, "nameServerAddr is blank");
        }

        DefaultMQProducer producer;

        producer = new DefaultMQProducer(properties.getGroupName());

        producer.setNamesrvAddr(properties.getNamesrvAddr());

        producer.setMaxMessageSize(properties.getProducerMaxMessageSize());
        producer.setSendMsgTimeout(properties.getProducerSendMsgTimeout());

        producer.setRetryTimesWhenSendAsyncFailed(properties.getProducerRetryTimesWhenSendFailed());

        try {
            producer.start();
            log.info("producer is start ! groupName:{}, namesrvAddr:{}", properties.getGroupName(), properties.getNamesrvAddr());
        } catch (MQClientException e) {
            log.error(String.format("producer is error {}", e.getMessage(), e));
            throw e;
        }
        return producer;
    }

    @PostConstruct
    public void initConsumer() {
        Map<String, AbstractRocketConsumer> consumers = applicationContext.getBeansOfType(AbstractRocketConsumer.class);
        if (consumers == null || consumers.size() == 0) {
            log.info("init rocket consumer 0");
        }

        Iterator<String> beans = consumers.keySet().iterator();
        while (beans.hasNext()) {
            String beanName = (String) beans.next();
            AbstractRocketConsumer consumer = consumers.get(beanName);
            consumer.init();
            createConsumer(consumer);
            log.info("init success consumer title {}, topic {}, tag{}", consumer.getConsumerTitle(), consumer.getTopics(), consumer.getTags());
        }
    }

    public void createConsumer(AbstractRocketConsumer arc) {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(this.properties.getGroupName());
        consumer.setNamesrvAddr(this.properties.getNamesrvAddr());
        consumer.setConsumeThreadMin(this.properties.getConsumerConsumeThreadMin());
        consumer.setConsumeThreadMax(this.properties.getConsumerConsumeThreadMax());
        consumer.setConsumeMessageBatchMaxSize(this.properties.getConsumerConsumeMessageBatchMaxSize());

        try {
            consumer.subscribe(arc.getTopics(), arc.getTags());
            consumer.start();
            arc.setMqPushConsumer(consumer);
        } catch (MQClientException e) {
            log.error("info consumer title {}", arc.getConsumerTitle(), e);
        }
    }
}
