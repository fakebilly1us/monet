package com.fakebilly.monet.basic.infrastructure.mq.producer;

import com.fakebilly.monet.basic.domain.mq.builder.MQMessageBuilder;
import com.fakebilly.monet.mq.producer.IProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Producer
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Component
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private IProducerService producerService;

    /**
     * 发送消息
     * @param messageBuilder
     **/
    public void sendNormal(MQMessageBuilder messageBuilder) {
        if (null == messageBuilder) {
            logger.info("MQ发送messageBuilder为空");
            return;
        }
        try {
            boolean result = false;
            logger.info("MQ发送消息messageBuilder:{}", messageBuilder.toString());
            if (null == messageBuilder.getOutSerializeType()) {
                result = producerService.sendNormalMsg(messageBuilder.buildNormal());
            } else {
                result = producerService.sendNormalMsg(messageBuilder.buildNormal(), messageBuilder.getOutSerializeType());
            }
            logger.info("MQ发送消息完成result:{}", result);
        } catch (Exception e) {
            logger.error("MQ发送消息失败,e:{}", e);
        }

    }

    /**
     * 发送顺序消息
     * @param messageBuilder
     **/
    public void sendOrder(MQMessageBuilder messageBuilder) {
        if (null == messageBuilder) {
            logger.info("MQ发送顺序消息messageBuilder为空");
            return;
        }
        try {
            boolean result = false;
            logger.info("MQ发送顺序消息开始messageBuilder:{}", messageBuilder.toString());
            if (null == messageBuilder.getOutSerializeType()) {
                result = producerService.sendOrderMsg(messageBuilder.buildOrder());
            } else {
                result = producerService.sendOrderMsg(messageBuilder.buildOrder(), messageBuilder.getOutSerializeType());
            }
            logger.info("MQ发送顺序消息完成result:{}", result);
        } catch (Exception e) {
            logger.error("MQ发送顺序消息失败,e:{}", e);
        }

    }

}
