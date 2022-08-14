package com.fakebilly.monet.basic.domain.mq.handler;

import com.fakebilly.monet.basic.domain.enums.CodeEnum;
import com.fakebilly.monet.basic.domain.utils.Assert;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * MessageHandlerFactory
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Component
public class MessageHandlerFactory implements ApplicationContextAware {

    @Autowired
    private static Map<String, IMessageHandler> strategyMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, IMessageHandler> callbackEventHandlerMap = applicationContext.getBeansOfType(IMessageHandler.class);
        callbackEventHandlerMap.forEach((k, v) -> strategyMap.put(getConfigCode(v.getTopic(), v.getTag()), v));
    }

    public static IMessageHandler getHandler(String topic, String tag) {
        String configCode = getConfigCode(topic, tag);
        IMessageHandler consumerService = strategyMap.get(configCode);
        Assert.notNull(consumerService, CodeEnum.ERROR, "未查询到对应消息处理类");
        return consumerService;
    }

    private static String getConfigCode(String topic, String tag) {
        return topic + "_" + tag;
    }


}
