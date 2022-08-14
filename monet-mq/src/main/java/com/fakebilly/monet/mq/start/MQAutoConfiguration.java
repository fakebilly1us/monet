package com.fakebilly.monet.mq.start;

import com.fakebilly.monet.mq.client.ConsumerClient;
import com.fakebilly.monet.mq.client.ProducerClient;
import com.fakebilly.monet.mq.config.MQConfig;
import com.fakebilly.monet.mq.consumer.IConsumerService;
import com.fakebilly.monet.mq.consumer.impl.ConsumerServiceImpl;
import com.fakebilly.monet.mq.producer.IProducerService;
import com.fakebilly.monet.mq.producer.impl.ProducerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * MQAutoConfiguration
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Configuration
public class MQAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MQAutoConfiguration.class);

    @Value("${rocketmq.nacos.config.server-addr}")
    private String serverAddr;
    @Value("${rocketmq.nacos.config.namespace}")
    private String nameSpace;
    @Value("${rocketmq.nacos.rocketmq.dataId}")
    private String dataId;
    @Value("${rocketmq.nacos.rocketmq.group}")
    private String group;

    @PostConstruct
    public void init() {
        try {
            MQConfig.init(serverAddr, nameSpace, dataId, group);
        } catch (Exception e) {
            logger.error("init MQAutoConfiguration failed ", e);
        }
    }

    @Bean
    public IConsumerService getConsumerService() {
        return new ConsumerServiceImpl();
    }

    @Bean
    public IProducerService getProducerService() {
        return new ProducerServiceImpl();
    }

    @Bean
    public ConsumerClient getConsumerClient() {
        return new ConsumerClient();
    }

    @Bean
    public ProducerClient getProducerClient() {
        return new ProducerClient();
    }

}
