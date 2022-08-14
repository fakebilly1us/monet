package com.fakebilly.monet.redis.start;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.spring.util.NacosBeanUtils;
import com.fakebilly.monet.redis.client.RedisClient;
import com.fakebilly.monet.redis.service.IRedisService;
import com.fakebilly.monet.redis.service.impl.RedisServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Collection;
import java.util.Properties;

/**
 * RedisAutoConfiguration
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Configuration
public class RedisAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(RedisAutoConfiguration.class);

    @Value("${redis.nacos.config.server-addr}")
    private String serverAddr;
    @Value("${redis.nacos.config.namespace}")
    private String nameSpace;
    @Value("${redis.nacos.redisson.dataId}")
    private String dataId;
    @Value("${redis.nacos.redisson.group}")
    private String group;

    @PostConstruct
    public void init() {
        try {
            Collection<ConfigService> configServices = NacosBeanUtils.getNacosServiceFactoryBean().getConfigServices();
            ConfigService configService = null;
            if (configServices.isEmpty()) {
                Properties properties = new Properties();
                properties.setProperty(PropertyKeyConst.SERVER_ADDR, this.serverAddr);
                properties.setProperty(PropertyKeyConst.NAMESPACE, this.nameSpace);
                configService = NacosFactory.createConfigService(properties);
            } else {
                configService = configServices.iterator().next();
            }
            RedisClient.init(this.serverAddr, this.nameSpace, this.dataId, this.group, configService);
        } catch (Exception e) {
            logger.error("init RedisAutoConfiguration failed ", e);
        }
    }

    @Bean
    public IRedisService getRedisService() {
        return new RedisServiceImpl();
    }

    @PreDestroy
    public void destroy() {
        RedisClient.destroy();
    }
}
