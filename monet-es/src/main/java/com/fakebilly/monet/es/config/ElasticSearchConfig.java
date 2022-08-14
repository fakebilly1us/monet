package com.fakebilly.monet.es.config;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.fakebilly.monet.es.contants.EsConstant;
import com.fakebilly.monet.es.exception.ElasticSearchException;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * ElasticSearchConfig
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class ElasticSearchConfig {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchConfig.class);

    /**
     * 实例
     */
    private static volatile ElasticsearchClient client;

    private static final long TIMEOUT_MS = 5000;

    /**
     * 实例初始化
     * @param serverAddr
     * @param nameSpace
     * @param dataId
     * @param group
     * @param configServices
     * @throws ElasticSearchException
     */
    public static void init(String serverAddr, String nameSpace, String dataId, String group, ConfigService... configServices) throws ElasticSearchException {
        getInstance(serverAddr, nameSpace, dataId, group, configServices);
    }

    /**
     * 获取实例
     * @param serverAddr
     * @param nameSpace
     * @param dataId
     * @param group
     * @param configServices
     * @return
     * @throws ElasticSearchException
     */
    public static ElasticsearchClient getInstance(String serverAddr, String nameSpace, String dataId, String group,
                                                  ConfigService... configServices) throws ElasticSearchException {
        if (null == client) {
            synchronized (ElasticSearchConfig.class) {
                if (client == null) {
                    ElasticSearchConfigInfo config = getEsConfigInfo(serverAddr, nameSpace, dataId, group, configServices);
                    if (null == config) {
                        logger.error("init ElasticSearchClient failed");
                        throw new ElasticSearchException("init ElasticSearchClient failed");
                    }
                    // 填充 config 变量所需的配置信息
                    client = buildClient(config);
                    logger.info("init ElasticsearchClient success");
                }
            }
        }
        return client;
    }

    /**
     * 获取实例
     * @return
     */
    public static ElasticsearchClient getESClient() {
        return client;
    }

    /**
     * 设置实例
     * @param esClient
     */
    public static void setESClient(ElasticsearchClient esClient) {
        client = esClient;
    }

    /**
     * 缓存实例销毁
     */
    public static void destroy() {
        client = null;
        logger.info("destroy ElasticSearchClient");
    }

    /**
     * 构造实例属性
     * @param configInfo
     * @return
     */
    public static ElasticsearchClient buildClient(ElasticSearchConfigInfo configInfo) {
        List<HttpHost> list = configInfo.getHosts();
        HttpHost[] hosts = new HttpHost[list.size()];
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(configInfo.getUserName(), configInfo.getPassword()));
        RestClientBuilder restClientBuilder = RestClient.builder(list.toArray(hosts))
                .setHttpClientConfigCallback(httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
        ElasticsearchTransport transport = new RestClientTransport(restClientBuilder.build(), new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }

    /**
     * 构造实例属性
     * @param serverAddr
     * @param nameSpace
     * @param dataId
     * @param group
     * @param configServices
     * @return
     */
    public static ElasticSearchConfigInfo getEsConfigInfo(String serverAddr, String nameSpace, String dataId, String group,
                                                          ConfigService... configServices) {
        ConfigService configService = null;
        if (null != configServices && configServices.length > 0) {
            configService = configServices[0];
        }
        if (null == configService) {
            if (StrUtil.isBlank(serverAddr) || StrUtil.isBlank(nameSpace) || StrUtil.isBlank(dataId) || StrUtil.isBlank(group)) {
                logger.error("init ElasticSearchConfigInfo nacos config check failed, serverAddr:{}, nameSpace:{}, dataId:{}, group:{}", serverAddr, nameSpace, dataId, group);
                throw new ElasticSearchException("init ElasticSearchConfigInfo nacos config check failed");
            }

            return getEsConfigInfo(serverAddr, nameSpace, dataId, group);
        } else {
            if (StrUtil.isBlank(dataId) || StrUtil.isBlank(group)) {
                logger.error("init ElasticSearchConfigInfo nacos config check failed, dataId:{}, gruop:{}", dataId, group);
                throw new ElasticSearchException("init ElasticSearchConfigInfo nacos config check failed");
            }
            return getEsConfigInfo(configService, dataId, group);

        }
    }

    /**
     * 构造实例属性
     * @param serverAddr
     * @param nameSpace
     * @param dataId
     * @param group
     * @return
     */
    private static ElasticSearchConfigInfo getEsConfigInfo(String serverAddr, String nameSpace, String dataId, String group) {
        ElasticSearchConfigInfo configInfo = null;
        // 加载nacos配置
        try {
            Properties properties = new Properties();
            properties.setProperty(PropertyKeyConst.SERVER_ADDR, serverAddr);
            properties.setProperty(PropertyKeyConst.NAMESPACE, nameSpace);
            ConfigService esConfigService = NacosFactory.createConfigService(properties);
            if (null == esConfigService) {
                logger.error("getEsConfigInfo by ConfigService is null");
                return null;
            }
            String esConfig = esConfigService.getConfig(dataId, group, TIMEOUT_MS);
            if (StrUtil.isBlank(esConfig)) {
                logger.error("getEsConfigInfo by ConfigService is null");
                return null;
            }
            configInfo = buildEsConfigInfo(esConfigService, esConfig, dataId, group);
        } catch (NacosException e) {
            logger.error("getEsConfigInfo nacosException ", e);
        } catch (Exception e) {
            logger.error("getEsConfigInfo exception ", e);
        }
        return configInfo;
    }

    /**
     * 构造实例属性
     * @param configService
     * @param dataId
     * @param group
     * @return
     */
    private static ElasticSearchConfigInfo getEsConfigInfo(ConfigService configService, String dataId, String group) {
        ElasticSearchConfigInfo configInfo = null;
        try {
            if (null == configService) {
                logger.error("getEsConfigInfo by ConfigService is null");
                return null;
            }
            String esConfig = configService.getConfig(dataId, group, TIMEOUT_MS);
            configInfo = buildEsConfigInfo(configService, esConfig, dataId, group);
        } catch (NacosException e) {
            logger.error("getEsConfigInfo nacosException ", e);
        } catch (Exception e) {
            logger.error("getEsConfigInfo exception ", e);
        }
        return configInfo;
    }

    private static ElasticSearchConfigInfo buildEsConfigInfo(ConfigService configService, String esConfig, String dataId, String group) throws Exception {
        ElasticSearchConfigInfo configInfo = buildEsConfigInfo(esConfig);
        configService.addListener(dataId, group, new ElasticSearchConfigNacosListener());
        return configInfo;
    }

    /**
     * 构造实例属性
     * @param esConfig
     * @return
     * @throws Exception
     */
    public static ElasticSearchConfigInfo buildEsConfigInfo(String esConfig) throws Exception {
        ElasticSearchConfigInfo configInfo = new ElasticSearchConfigInfo();
        Properties properties = new Properties();
        properties.load(new StringReader(esConfig));
        String scheme = properties.getProperty(EsConstant.ES_SERVER_SCHEME);
        String serverAddr = properties.getProperty(EsConstant.ES_SERVER_URL);
        List<HttpHost> hosts = new ArrayList<>();
        List<String> clusterList = Arrays.asList(serverAddr.split(","));
        if (CollectionUtil.isNotEmpty(clusterList)) {
            clusterList.forEach(e -> {
                e = e.replace(EsConstant.ES_CONFIG_FOR_REPLACE_HTTP, "").replace(EsConstant.ES_CONFIG_FOR_REPLACE_HTTPS, "");
                String address = e.split(":")[0];
                int port = Integer.parseInt(e.split(":")[1]);
                hosts.add(new HttpHost(address, port, scheme));

            });
        }
        configInfo.setUserName(properties.getProperty(EsConstant.ES_SERVER_USERNAME));
        configInfo.setPassword(properties.getProperty(EsConstant.ES_SERVER_PASSWORD));
        configInfo.setHosts(hosts);
        return configInfo;
    }
}
