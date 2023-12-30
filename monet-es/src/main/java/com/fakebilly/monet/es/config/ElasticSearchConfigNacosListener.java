package com.fakebilly.monet.es.config;

import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.alibaba.nacos.api.config.listener.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;

/**
 * NacosRedissonConfigListener
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class ElasticSearchConfigNacosListener implements Listener {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConfigNacosListener.class);

    @Override
    public Executor getExecutor() {
        return null;
    }

    @Override
    public void receiveConfigInfo(String receiveConfigInfo) {
        try {
            if (StrUtil.isBlank(receiveConfigInfo)) {
                logger.error("ElasticSearchConfigNacosListener receiveConfigInfo is null");
                return;
            }
            // 再重新加载
            ElasticSearchConfigInfo configInfo = ElasticSearchConfig.buildEsConfigInfo(receiveConfigInfo);
            // 先销毁
            ElasticSearchConfig.destroy();
            // 填充 config 变量所需的配置信息
            ElasticsearchClient client = ElasticSearchConfig.buildClient(configInfo);
            ElasticSearchConfig.setESClient(client);
            logger.info("ElasticSearchConfigNacosListener receiveConfigInfo success");
        } catch (Exception e) {
            logger.error("ElasticSearchConfigNacosListener receiveConfigInfo exception ", e);
        }
    }
}
