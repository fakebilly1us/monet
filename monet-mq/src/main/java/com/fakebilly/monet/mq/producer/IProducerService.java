package com.fakebilly.monet.mq.producer;

import com.fakebilly.monet.mq.enums.SerializeTypeEnum;
import com.fakebilly.monet.mq.exception.MQException;
import com.fakebilly.monet.mq.message.MessageBuilder;

import java.util.List;

/**
 * IProducerService
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public interface IProducerService {

    /**
     * 发送普通消息
     * @param msgBuilder
     * @return boolean
     **/
    boolean sendNormalMsg(MessageBuilder msgBuilder) throws MQException;


    /**
     * 发送普通消息
     * @param msgBuilder
     * @param serializeType
     * @return boolean
     **/
    boolean sendNormalMsg(MessageBuilder msgBuilder, SerializeTypeEnum serializeType) throws MQException;


    /**
     * 发送顺序消息
     * @param msgBuilderList
     * @return boolean
     **/
    boolean sendOrderMsg(List<MessageBuilder> msgBuilderList) throws MQException;


    /**
     * 发送顺序消息
     * @param msgBuilderList
     * @param serializeType
     * @return boolean
     **/
    boolean sendOrderMsg(List<MessageBuilder> msgBuilderList, SerializeTypeEnum serializeType) throws MQException;


    /**
     * 发送异步消息
     * @param msgBuilder
     **/
    void sendOnewayMsg(MessageBuilder msgBuilder) throws MQException;

    /**
     * 发送异步消息
     * @param msgBuilder
     * @param serializeType
     **/
    void sendOnewayMsg(MessageBuilder msgBuilder, SerializeTypeEnum serializeType) throws MQException;

    /**
     * 发送批量消息
     * @param messageBuilderList
     * @return boolean
     **/
    boolean sendBatchMsg(List<MessageBuilder> messageBuilderList) throws MQException;

    /**
     * 发送带超时时间的批量消息
     * @param messageBuilderList
     * @param timeout
     * @return boolean
     **/
    boolean sendBatchMsg(List<MessageBuilder> messageBuilderList, long timeout) throws MQException;
}
