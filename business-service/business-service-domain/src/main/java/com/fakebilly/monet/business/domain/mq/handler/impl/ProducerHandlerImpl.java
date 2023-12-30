package com.fakebilly.monet.business.domain.mq.handler.impl;

import cn.hutool.core.map.MapUtil;
import com.fakebilly.monet.business.domain.adapter.MqAdapter;
import com.fakebilly.monet.business.domain.constants.MQConstant;
import com.fakebilly.monet.business.domain.enums.CodeEnum;
import com.fakebilly.monet.business.domain.mq.builder.MQMessageBuilder;
import com.fakebilly.monet.business.domain.mq.handler.IMessageHandler;
import com.fakebilly.monet.core.exception.BusinessException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * ProducerHandlerImpl
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Component
public class ProducerHandlerImpl implements IMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(ProducerHandlerImpl.class);

    @Autowired
    private MqAdapter mqAdapter;

    @Override
    public String getTopic() {
        return MQConstant.TOPIC_BUSINESS;
    }

    @Override
    public String getTag() {
        return MQConstant.TAG_PRODUCER;
    }

    @Override
    public String getType() {
        return MQConstant.TYPE_PRODUCER;
    }

    @Override
    public String getConsumerType() {
        return MQConstant.MESSAGE_NORMAL;
    }


    @Override
    public boolean producer(Map<String, Object> messageBody) {
        if (MapUtil.isEmpty(messageBody)) {
            return true;
        }
        MQMessageBuilder messageBuilder = MQMessageBuilder.create()
                .topic(MQConstant.TOPIC_BASIC)
                .tag(getTag())
                .key(String.valueOf(System.currentTimeMillis()))
                .serializeType(MQMessageBuilder.SerializeType.JSON_SERIALIZE)
                .message(new MQMessageBuilder.MQMessage(String.valueOf(System.currentTimeMillis()), messageBody));
        logger.info("ProducerHandler发送MQ,messageBuilder:{}", messageBuilder.toString());
        mqAdapter.sendNormal(messageBuilder);
        return true;
    }

    @Override
    public boolean consumer(MessageExt msg) {
        throw new BusinessException(CodeEnum.ERROR_MQ.getCode(), "不支持消费方法");
    }


}
