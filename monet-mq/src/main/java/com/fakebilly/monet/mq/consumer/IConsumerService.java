package com.fakebilly.monet.mq.consumer;

import com.fakebilly.monet.mq.business.ConsumeBusinessLogicHandler;
import com.fakebilly.monet.mq.exception.MQException;

import java.util.Set;

/**
 * IConsumerService
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public interface IConsumerService {

    /**
     * 普通消息
     * @param topic
     * @param tags
     * @param groupId
     * @param logicHandler
     **/
    void receiveNormalMsg(String topic, Set<String> tags, String groupId, ConsumeBusinessLogicHandler logicHandler) throws MQException;

    /**
     * 顺序消息
     * @param topic
     * @param tags
     * @param groupId
     * @param logicHandler
     **/
    void receiveOrderMsg(String topic, Set<String> tags, String groupId, ConsumeBusinessLogicHandler logicHandler) throws MQException;

}
