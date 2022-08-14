package com.fakebilly.monet.redis.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.api.config.listener.Listener;
import com.fakebilly.monet.redis.client.RedisClient;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;

/**
 * RedissonConfigNacosListener
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class RedissonConfigNacosListener implements Listener {

    private static final Logger logger = LoggerFactory.getLogger(RedissonConfigNacosListener.class);

    @Override
    public Executor getExecutor() {
        return null;
    }

    @Override
    public void receiveConfigInfo(String configInfo) {
        try {
            if (StrUtil.isBlank(configInfo)) {
                logger.error("RedissonConfigNacosListener receiveConfigInfo is null");
                return;
            }
            // 再重新加载
            Config config = Config.fromYAML(configInfo);
            // 先销毁
            RedisClient.destroy();
            // 填充 config 变量所需的配置信息
            RedissonClient client = Redisson.create(config);
            RedisClient.setRedissonClient(client);
            logger.info("RedissonConfigNacosListener receiveConfigInfo success");
        } catch (Exception e) {
            logger.error("RedissonConfigNacosListener receiveConfigInfo exception ", e);
        }
    }
}
