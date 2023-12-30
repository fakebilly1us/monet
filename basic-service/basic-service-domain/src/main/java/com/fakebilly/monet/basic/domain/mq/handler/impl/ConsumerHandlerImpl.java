package com.fakebilly.monet.basic.domain.mq.handler.impl;

import cn.hutool.core.map.MapUtil;
import com.fakebilly.monet.basic.domain.constants.MQConstant;
import com.fakebilly.monet.basic.domain.enums.CodeEnum;
import com.fakebilly.monet.basic.domain.mq.handler.IMessageHandler;
import com.fakebilly.monet.core.exception.BusinessException;
import com.fakebilly.monet.mq.message.MessageBodyModel;
import com.fakebilly.monet.mq.utils.MessageUtil;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * ConsumerHandlerImpl
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Component
public class ConsumerHandlerImpl implements IMessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerHandlerImpl.class);

    @Override
    public String getTopic() {
        return MQConstant.TOPIC_UP_STREAM;
    }

    @Override
    public String getTag() {
        return MQConstant.TAG_UP_STREAM;
    }

    @Override
    public String getType() {
        return MQConstant.TYPE_CONSUMER;
    }

    @Override
    public String getConsumerType() {
        return MQConstant.MESSAGE_NORMAL;
    }

    @Override
    public boolean producer(Map<String, Object> messageBody) {
        throw new BusinessException(CodeEnum.ERROR_MQ.getCode(), "不支持生产消息方法");
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean consumer(MessageExt msg) {
        if (null == msg) {
            logger.info("ConsumerHandler接收消息为空");
            return false;
        }
        MessageBodyModel messageBodyModel = MessageUtil.deserializeMessageBody(msg);
        logger.info("ConsumerHandler接收消息,messageBodyModel:{}", messageBodyModel.toString());
        Map<String, Object> messageBody = messageBodyModel.getMessageBody();
        if (MapUtil.isEmpty(messageBody)) {
            return false;
        }

        return true;
    }


}
