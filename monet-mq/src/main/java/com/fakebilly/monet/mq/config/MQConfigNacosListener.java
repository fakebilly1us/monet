package com.fakebilly.monet.mq.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.api.config.listener.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

/**
 * NacosMqConfigListener
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Component
public class MQConfigNacosListener implements Listener {

    private static final Logger logger = LoggerFactory.getLogger(MQConfig.class);

    @Override
    public Executor getExecutor() {
        return null;
    }

    @Override
    public void receiveConfigInfo(String receiveConfigInfo) {
        try {
            if (StrUtil.isBlank(receiveConfigInfo)) {
                logger.error("MQConfigNacosListener receiveConfigInfo is null");
                return;
            }
            MQConfig.stop();
            MQConfigInfo configInfo = MQConfig.buildMQConfigInfo(receiveConfigInfo);
            MQConfig.init(configInfo);
            MQConfig.start();
            logger.info("MQConfigNacosListener receiveConfigInfo success!");
        } catch (Exception e) {
            logger.error("MQConfigNacosListener receiveConfigInfo exception ", e);
        }
    }

}
