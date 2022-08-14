package com.fakebilly.monet.redis.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.fakebilly.monet.redis.exception.RedissonException;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * RedissonConfig
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class RedissonConfig {

    private static final Logger logger = LoggerFactory.getLogger(RedissonConfig.class);

    private static final long TIMEOUT_MS = 5000;

    public static Config getRedissonConfig(String serverAddr, String nameSpace, String dataId, String group, ConfigService... configServices) throws RedissonException {
        ConfigService configService = null;
        if (null != configServices && configServices.length > 0) {
            configService = configServices[0];
        }
        if (null == configService) {
            if (StrUtil.isBlank(serverAddr) || StrUtil.isBlank(nameSpace) || StrUtil.isBlank(dataId) || StrUtil.isBlank(group)) {
                logger.error("RedissonConfig ConfigService check failed, serverAddr:{}, nameSpace:{}, dataId:{}, group:{}", serverAddr, nameSpace, dataId, group);
                throw new RedissonException("RedissonConfig ConfigService check failed");
            }

            return getRedissonConfig(serverAddr, nameSpace, dataId, group);
        } else {
            if (StrUtil.isBlank(dataId) || StrUtil.isBlank(group)) {
                logger.error("RedissonConfig nacos config check failed, dataId:{}, gruop:{}", dataId, group);
                throw new RedissonException("RedissonConfig nacos config check failed!");
            }
            return getRedissonConfig(configService, dataId, group);

        }

    }

    private static Config getRedissonConfig(String serverAddr, String nameSpace, String dataId, String group) {
        Config config = null;
        // 加载nacos配置
        try {
            Properties properties = new Properties();
            properties.setProperty(PropertyKeyConst.SERVER_ADDR, serverAddr);
            properties.setProperty(PropertyKeyConst.NAMESPACE, nameSpace);
            ConfigService cacheConfigService = NacosFactory.createConfigService(properties);
            if (null == cacheConfigService) {
                logger.error("getRedissonConfig by ConfigService is null");
                return null;
            }
            String cacheConfig = cacheConfigService.getConfig(dataId, group, TIMEOUT_MS);
            if (StrUtil.isBlank(cacheConfig)) {
                logger.error("getRedissonConfig by ConfigService is null");
                return null;
            }
            config = buildRedissonConfig(cacheConfigService, cacheConfig, dataId, group);
        } catch (NacosException e) {
            logger.error("getRedissonConfig nacosException ", e);
        } catch (Exception e) {
            logger.error("getRedissonConfig exception ", e);
        }
        return config;
    }

    private static Config getRedissonConfig(ConfigService configService, String dataId, String group) {
        Config config = null;
        try {
            if (null == configService) {
                logger.error("getRedissonConfig by ConfigService is null");
                return null;
            }
            String cacheConfig = configService.getConfig(dataId, group, TIMEOUT_MS);
            config = buildRedissonConfig(configService, cacheConfig, dataId, group);
        } catch (NacosException e) {
            logger.error("getRedissonConfig nacosException ", e);
        } catch (Exception e) {
            logger.error("getRedissonConfig exception ", e);
        }
        return config;
    }

    private static Config buildRedissonConfig(ConfigService configService, String cacheConfig, String dataId, String group) throws Exception {
        Config config = Config.fromYAML(cacheConfig);
        configService.addListener(dataId, group, new RedissonConfigNacosListener());
        return config;
    }

}
