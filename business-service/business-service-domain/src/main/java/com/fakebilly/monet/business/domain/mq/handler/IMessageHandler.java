package com.fakebilly.monet.business.domain.mq.handler;

import org.apache.rocketmq.common.message.MessageExt;

import java.util.Map;

/**
 * IMessageHandler
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public interface IMessageHandler {

    /**
     * topic
     * @param
     * @return java.lang.String
     **/
    String getTopic();

    /**
     * tag
     * @param
     * @return java.lang.String
     **/
    String getTag();

    /**
     * type
     * @param
     * @return java.lang.String
     **/
    String getType();

    /**
     * type
     * @param
     * @return java.lang.String
     **/
    String getConsumerType();

    /**
     * 生产消息
     * @param messageBody
     * @return boolean
     **/
    boolean producer(Map<String, Object> messageBody);

    /**
     * 消费消息
     * @param msg
     * @return boolean
     **/
    boolean consumer(MessageExt msg);

}
