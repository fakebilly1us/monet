package com.fakebilly.monet.mq.config;

import cn.hutool.core.util.StrUtil;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.fakebilly.monet.mq.constant.MqConfigConstant;
import com.fakebilly.monet.mq.enums.ClusterNameEnum;
import com.fakebilly.monet.mq.exception.MQException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MQConfig
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class MQConfig {

    private static final Logger logger = LoggerFactory.getLogger(MQConfig.class);

    private static volatile boolean start = false;

    private static final long TIMEOUT_MS = 5000;

    private static volatile ConcurrentHashMap<ClusterNameEnum, MQConfigInfo> CONSUMER_CONFIG_MAP = new ConcurrentHashMap<>();
    private static volatile ConcurrentHashMap<ClusterNameEnum, MQConfigInfo> PRODUCER_CONFIG_MAP = new ConcurrentHashMap<>();
    private static volatile MQConfigInfo MQ_CONFIG_INFO = new MQConfigInfo();

    public static void init(String serverAddr, String nameSpace, String dataId, String group, ConfigService... configServices) {
        if (!start) {
            synchronized (MQConfig.class) {
                if (!start) {
                    MQConfigInfo configInfo = getMQConfigInfo(serverAddr, nameSpace, dataId, group, configServices);
                    init(configInfo);
                }
                start = true;
            }
        }
    }

    public static void init(MQConfigInfo configInfo) {
        if (null == configInfo) {
            logger.error("init MQConfig config is null");
            throw new MQException("init MQConfig config is null");
        }
        if (!CONSUMER_CONFIG_MAP.containsKey(ClusterNameEnum.CENTER_CLUSTER)) {
            CONSUMER_CONFIG_MAP.put(ClusterNameEnum.CENTER_CLUSTER, configInfo);
        }
        if (!PRODUCER_CONFIG_MAP.containsKey(ClusterNameEnum.CENTER_CLUSTER)) {
            PRODUCER_CONFIG_MAP.put(ClusterNameEnum.CENTER_CLUSTER, configInfo);
        }
        MQ_CONFIG_INFO = configInfo;
    }

    private static MQConfigInfo getMQConfigInfo(String serverAddr, String nameSpace, String dataId, String group,
                                                ConfigService... configServices) {
        ConfigService configService = null;
        if (null != configServices && configServices.length > 0) {
            configService = configServices[0];
        }
        if (null == configService) {
            if (StrUtil.isBlank(serverAddr) || StrUtil.isBlank(nameSpace) || StrUtil.isBlank(dataId) || StrUtil.isBlank(group)) {
                logger.error("init MQConfig nacos config check failed, serverAddr:{}, nameSpace:{}, dataId:{}, group:{}", serverAddr, nameSpace, dataId, group);
                throw new MQException("init MQConfig nacos config check failed");
            }

            return getMQConfigInfo(serverAddr, nameSpace, dataId, group);
        } else {
            if (StrUtil.isBlank(dataId) || StrUtil.isBlank(group)) {
                logger.error("init MQConfigInfo nacos config check failed, dataId:{}, gruop:{}", dataId, group);
                throw new MQException("init MQConfigInfo nacos config check failed");
            }
            return getMQConfigInfo(configService, dataId, group);

        }
    }

    private static MQConfigInfo getMQConfigInfo(String serverAddr, String nameSpace, String dataId, String group) {
        MQConfigInfo configInfo = null;
        // 加载nacos配置
        try {
            Properties properties = new Properties();
            properties.setProperty(PropertyKeyConst.SERVER_ADDR, serverAddr);
            properties.setProperty(PropertyKeyConst.NAMESPACE, nameSpace);
            ConfigService configService = NacosFactory.createConfigService(properties);
            if (null == configService) {
                logger.error("getMQConfigInfo by ConfigService is null");
                return null;
            }
            String mqConfig = configService.getConfig(dataId, group, TIMEOUT_MS);
            if (StrUtil.isBlank(mqConfig)) {
                logger.error("getMQConfigInfo by ConfigService is null");
                return null;
            }
            configInfo = buildMQConfigInfo(configService, mqConfig, dataId, group);
        } catch (NacosException e) {
            logger.error("getMQConfigInfo nacosException ", e);
        } catch (Exception e) {
            logger.error("getMQConfigInfo exception ", e);
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
    private static MQConfigInfo getMQConfigInfo(ConfigService configService, String dataId, String group) {
        MQConfigInfo configInfo = null;
        try {
            if (null == configService) {
                logger.error("getMQConfigInfo by configService is null");
                return null;
            }
            String esConfig = configService.getConfig(dataId, group, TIMEOUT_MS);
            configInfo = buildMQConfigInfo(configService, esConfig, dataId, group);
        } catch (NacosException e) {
            logger.error("getMQConfigInfo nacosException ", e);
        } catch (Exception e) {
            logger.error("getMQConfigInfo exception ", e);
        }
        return configInfo;
    }

    private static MQConfigInfo buildMQConfigInfo(ConfigService configService, String esConfig, String dataId, String group) throws Exception {
        MQConfigInfo configInfo = buildMQConfigInfo(esConfig);
        configService.addListener(dataId, group, new MQConfigNacosListener());
        return configInfo;
    }

    public static MQConfigInfo buildMQConfigInfo(String mqConfig) throws Exception {
        MQConfigInfo configInfo = new MQConfigInfo();
        Properties properties = new Properties();
        properties.load(new StringReader(mqConfig));
        configInfo.setNamesrvAddr(properties.getProperty(MqConfigConstant.NAMESRV_ADDR));
        configInfo.setReconsumerTimes(Integer.valueOf(properties.getProperty(MqConfigConstant.RECONSUME_TIMES, MqConfigConstant.RECONSUME_TIMES_DEFAULT)));
        configInfo.setMaxReconsumeTimes(Integer.valueOf(properties.getProperty(MqConfigConstant.MAX_RECONSUME_TIMES, MqConfigConstant.MAX_RECONSUME_TIMES_DEFAULT)));
        configInfo.setSendMsgTimeout(Integer.valueOf(properties.getProperty(MqConfigConstant.SEND_MSG_TIMEOUT, MqConfigConstant.SEND_MSG_TIMEOUT_DEFAULT)));
        configInfo.setRetryTimesWhenSendFailed(Integer.valueOf(properties.getProperty(MqConfigConstant.RETRYTIMES_WHEN_SEND_FAILED, MqConfigConstant.RETRYTIMES_WHEN_SEND_FAILED_DEFAULT)));
        return configInfo;
    }


    public static MQConfigInfo getConsumeMqMQConfigInfo(ClusterNameEnum clusterName) {
        if (null == clusterName) {
            return null;
        }
        return CONSUMER_CONFIG_MAP.get(clusterName);
    }

    public static MQConfigInfo getProducerMqMQConfigInfo(ClusterNameEnum clusterName) {
        if (null == clusterName) {
            return null;
        }
        return PRODUCER_CONFIG_MAP.get(clusterName);
    }

    public static MQConfigInfo getConfigInfo() {
        return MQ_CONFIG_INFO;
    }

    public static void stop() {
        start = false;
    }

    public static void start() {
        start = true;
    }

}
