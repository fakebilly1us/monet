package com.fakebilly.monet.es.start;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.fakebilly.monet.es.config.ElasticSearchConfig;
import com.fakebilly.monet.es.service.IElasticSearchService;
import com.fakebilly.monet.es.service.impl.ElasticSearchServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * ElasticSearchAutoConfiguration
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Configuration
public class ElasticSearchAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchAutoConfiguration.class);

    @Value("${es.nacos.config.server-addr}")
    private String serverAddr;
    @Value("${es.nacos.config.namespace}")
    private String nameSpace;
    @Value("${es.nacos.es.dataId}")
    private String dataId;
    @Value("${es.nacos.es.group}")
    private String group;

    @PostConstruct
    public void init() {
        try {
            ElasticSearchConfig.init(serverAddr, nameSpace, dataId, group);
        } catch (Exception e) {
            logger.error("init ElasticSearchAutoConfiguration exception ", e);
        }
    }

    @Bean(name = "ESClient")
    public ElasticsearchClient getESClient() {
        return ElasticSearchConfig.getESClient();
    }

    @Bean
    public IElasticSearchService getElasticSearchService() {
        return new ElasticSearchServiceImpl();
    }
}
