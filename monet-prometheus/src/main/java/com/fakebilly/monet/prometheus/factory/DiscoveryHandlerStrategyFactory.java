package com.fakebilly.monet.prometheus.factory;

import com.fakebilly.monet.prometheus.enums.DiscoveryTypeEnum;
import com.fakebilly.monet.prometheus.handler.DiscoveryHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * BuildDiscoveryHandlerStrategyFactory
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Component
public class DiscoveryHandlerStrategyFactory implements ApplicationContextAware {

    @Autowired
    public static Map<String, DiscoveryHandler> strategyMap = new HashMap<>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, DiscoveryHandler> buildHandlerMap = applicationContext.getBeansOfType(DiscoveryHandler.class);
        buildHandlerMap.forEach((key, value) -> strategyMap.put(value.getType(), value));
    }

    public static DiscoveryHandler getHandler(String type) {
        DiscoveryHandler handler = strategyMap.get(type);
        return handler == null ? strategyMap.get(DiscoveryTypeEnum.FILE.getCode()) : handler;
    }


}
