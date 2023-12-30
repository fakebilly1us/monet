package com.fakebilly.monet.redis.client;

import com.alibaba.nacos.api.config.ConfigService;
import com.fakebilly.monet.redis.config.RedissonConfig;
import com.fakebilly.monet.redis.exception.RedissonException;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RedisClient
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class RedisClient {

    private static final Logger logger = LoggerFactory.getLogger(RedisClient.class);

    private static volatile RedissonClient client;

    public static RedissonClient getInstance(String serverAddr, String nameSpace, String dataId, String group,
                                             ConfigService... configServices) throws RedissonException {
        if (null == client) {
            synchronized (RedisClient.class) {
                if (client == null) {
                    Config config = RedissonConfig.getRedissonConfig(serverAddr, nameSpace, dataId, group, configServices);
                    if (null == config) {
                        logger.error("init RedisClient failed");
                        throw new RedissonException("RedisClient Config is not existed");
                    }
                    // 填充 config 变量所需的配置信息
                    client = Redisson.create(config);
                    logger.info("init RedisClient success");
                }
            }
        }
        return client;
    }

    /**
     * 缓存实例初始化
     */
    public static void init(String serverAddr, String nameSpace, String dataId, String group, ConfigService... configServices) throws RedissonException {
        getInstance(serverAddr, nameSpace, dataId, group, configServices);
    }

    public static RedissonClient getRedissonClient() {
        return client;
    }

    public static void setRedissonClient(RedissonClient reloadRedissonClient) {
        client = reloadRedissonClient;
    }

    /**
     * 缓存实例销毁
     */
    public static void destroy() {
        if (null != client && !client.isShutdown()) {
            client.shutdown();
            logger.info("destroy RedisClient");
        }
    }


}
