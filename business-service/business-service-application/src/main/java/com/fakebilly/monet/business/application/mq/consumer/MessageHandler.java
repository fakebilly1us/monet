package com.fakebilly.monet.business.application.mq.consumer;

import com.fakebilly.monet.business.domain.mq.handler.IMessageHandler;
import com.fakebilly.monet.business.domain.mq.handler.MessageHandlerFactory;
import com.fakebilly.monet.mq.business.ConsumeBusinessLogicHandler;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * MessageHandler
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Service("messageHandler")
public class MessageHandler implements ConsumeBusinessLogicHandler {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);


    @Override
    public boolean businessLogicExecute(MessageExt msg) {
        String topic = msg.getTopic();
        String tags = msg.getTags();
        try {
            logger.info("messageHandler处理消息,topic:{},tags:{}", topic, tags);
            IMessageHandler handler = MessageHandlerFactory.getHandler(topic, tags);
            return handler.consumer(msg);
        } catch (Exception e) {
            logger.error("处理消息发生异常,等待MQ重试", e);
            return false;
        }
    }
}
