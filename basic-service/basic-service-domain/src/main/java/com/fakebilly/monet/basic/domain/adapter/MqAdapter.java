package com.fakebilly.monet.basic.domain.adapter;

import com.fakebilly.monet.basic.domain.mq.builder.MQMessageBuilder;

/**
 * MqAdapter
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public interface MqAdapter {

    /**
     * 发送消息
     * @param messageBuilder
     **/
    void sendNormal(MQMessageBuilder messageBuilder);

    /**
     * 发送顺序消息
     * @param messageBuilder
     **/
    void sendOrder(MQMessageBuilder messageBuilder);

}
