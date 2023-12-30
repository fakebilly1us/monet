package com.fakebilly.monet.mq.business;

import org.apache.rocketmq.common.message.MessageExt;

/**
 * ConsumeBusinessLogicHandler
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public interface ConsumeBusinessLogicHandler {

    /**
     * 成功收到 mqserver 的消息后的业务逻辑处理
     * @param msg
     * @return
     */
    boolean businessLogicExecute(MessageExt msg);
}
