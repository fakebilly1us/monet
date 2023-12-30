package com.fakebilly.monet.business.application.mq.consumer;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.fakebilly.monet.business.domain.constants.MQConstant;
import com.fakebilly.monet.business.domain.mq.handler.IMessageHandler;
import com.fakebilly.monet.business.domain.utils.ApplicationUtil;
import com.fakebilly.monet.mq.business.ConsumeBusinessLogicHandler;
import com.fakebilly.monet.mq.consumer.IConsumerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

/**
 * Consumer
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Component
public class Consumer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    private final static String SERVICE = "business-service";

    @Autowired
    private IConsumerService consumerService;

    @Resource(name = "messageHandler")
    private ConsumeBusinessLogicHandler messageLogicHandler;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            logger.info("MQ Consumer Init Start.");
            try {
                Map<String, Map<String, Set<String>>> listenerMap = getListener();
                if (MapUtil.isNotEmpty(listenerMap)) {
                    // k type; v map
                    listenerMap.forEach((k, v) -> {
                        if (MapUtil.isNotEmpty(v)) {
                            v.forEach((topic, tags) -> {
                                if (StrUtil.equals(k, MQConstant.MESSAGE_ORDER)) {
                                    consumerService.receiveOrderMsg(topic, tags, SERVICE, messageLogicHandler);
                                } else {
                                    consumerService.receiveNormalMsg(topic, tags, SERVICE, messageLogicHandler);
                                }
                            });
                        }
                    });
                }
            } catch (Exception e) {
                logger.info("MQ Consumer Init Failed. Exception ", e);
            }
            logger.info("MQ Consumer Init Success.");
        }
    }

    private Map<String, Map<String, Set<String>>> getListener() {
        Map<String, Map<String, Set<String>>> listenerMap = MapUtil.newHashMap();
        Map<String, IMessageHandler> handlerMap = ApplicationUtil.getBeansOfType(IMessageHandler.class);
        if (MapUtil.isEmpty(handlerMap)) {
            return listenerMap;
        }
        handlerMap.forEach((k, v) -> {
            String topic = v.getTopic();
            String tag = v.getTag();
            if (StrUtil.isAllNotBlank(topic, tag)) {
                String type = v.getType();
                // 消费者做监听 不做缺省处理
                if (StrUtil.equals(type, MQConstant.TYPE_CONSUMER)) {
                    // 普通/顺序消息 不做缺省处理
                    String consumerType = v.getConsumerType();
                    if (StrUtil.equalsAny(consumerType, MQConstant.MESSAGE_NORMAL, MQConstant.MESSAGE_ORDER)) {
                        Map<String, Set<String>> map = listenerMap.get(consumerType);
                        if (MapUtil.isEmpty(map)) {
                            map = MapUtil.newHashMap();
                        }
                        Set<String> tags = map.get(topic);
                        if (CollectionUtil.isEmpty(tags)) {
                            tags = CollectionUtil.newHashSet();
                        }
                        tags.add(tag);
                        map.put(topic, tags);

                        Map<String, Set<String>> types = listenerMap.get(consumerType);
                        if (MapUtil.isEmpty(types)) {
                            types = MapUtil.newHashMap();
                        }
                        types.putAll(map);
                        listenerMap.put(consumerType, types);
                    }
                }
            }
        });
        logger.info("MQ Consumer Init Map:{}", JSON.toJSONString(listenerMap));
        return listenerMap;
    }
}