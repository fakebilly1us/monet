package com.fakebilly.monet.prometheus.handler.zookeeper;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.fakebilly.monet.prometheus.config.MonetMonitorConfigInfo;
import com.fakebilly.monet.prometheus.enums.DiscoveryTypeEnum;
import com.fakebilly.monet.prometheus.handler.DiscoveryHandler;
import com.fakebilly.monet.prometheus.util.OSUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DiscoveryZookeeperHandlerImpl
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Component
public class DiscoveryZookeeperHandlerImpl implements DiscoveryHandler {

    private static final Logger logger = LoggerFactory.getLogger(DiscoveryZookeeperHandlerImpl.class);

    private static final String ZOOKEEPER_ROOT_PATH = "/monitor/prometheus/discovery";

    private static String ipv4 = "";

    @Autowired
    private MonetMonitorConfigInfo monetMonitorConfigInfo;

    @Override
    public String getType() {
        return DiscoveryTypeEnum.ZOOKEEPER.getCode();
    }

    @Override
    public void discovery() {
        logger.warn("zookeeper discovery is not supported yet");
    }

    private void dealDiscovery() {
        CuratorFramework client = null;
        try {
            ipv4 = OSUtil.getIPV4ExcludeLocal();
            if (StrUtil.isBlank(ipv4)) {
                logger.warn("DiscoveryHandler deal zookeeper cannot find ipv4");
                return;
            }
            client = DiscoveryZookeeperClient.getClientThenStart(monetMonitorConfigInfo.getDiscoveryZookeeperAddress());
            String discoveryPath = ZOOKEEPER_ROOT_PATH + "/" + monetMonitorConfigInfo.getApplicationName();
            Stat checkExists = client.checkExists().forPath(discoveryPath);
            if (null == checkExists) {
                byte[] data = JSON.toJSONString(buildConfig()).getBytes(StandardCharsets.UTF_8);
                String create = client.create().creatingParentContainersIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .forPath(discoveryPath, data);
                if (StrUtil.isBlank(create)) {
                    logger.warn("DiscoveryHandler deal zookeeper create failed");
                }
            } else {
                boolean reset = false;
                byte[] data = client.getData().forPath(discoveryPath);
                if (null != data) {
                    String dataStr = new String(data, 0, data.length, StandardCharsets.UTF_8);
                    ServerSetSdConfig config = JSON.parseObject(dataStr, ServerSetSdConfig.class);
                    ServiceEndpoint serviceEndpoint = config.getServiceEndpoint();
                    Map<String, ServiceEndpoint> additionalEndpoints = config.getAdditionalEndpoints();
                    if (null == serviceEndpoint) {
                        serviceEndpoint = new ServiceEndpoint(ipv4, monetMonitorConfigInfo.getServerPort());
                        config.setServiceEndpoint(serviceEndpoint);
                        reset = true;
                    } else {
                        String host = serviceEndpoint.getHost();
                        String port = serviceEndpoint.getPort();
                        if (!StrUtil.equals(host, ipv4) || !StrUtil.equals(port, monetMonitorConfigInfo.getServerPort())) {
                            serviceEndpoint = new ServiceEndpoint(ipv4, monetMonitorConfigInfo.getServerPort());
                            config.setServiceEndpoint(serviceEndpoint);
                            reset = true;
                        }
                    }
                    if (MapUtil.isEmpty(additionalEndpoints)) {
                        additionalEndpoints = new LinkedHashMap<>();
                        additionalEndpoints.put(buildEndPointKey(), new ServiceEndpoint(ipv4, monetMonitorConfigInfo.getServerPort()));
                        config.setAdditionalEndpoints(additionalEndpoints);
                        reset = true;
                    } else {
                        if (!additionalEndpoints.containsKey(buildEndPointKey())) {
                            additionalEndpoints.put(buildEndPointKey(), new ServiceEndpoint(ipv4, monetMonitorConfigInfo.getServerPort()));
                            config.setAdditionalEndpoints(additionalEndpoints);
                            reset = true;
                        }
                    }
                    if (reset) {
                        data = JSON.toJSONString(config).getBytes(StandardCharsets.UTF_8);
                    }
                } else {
                    data = JSON.toJSONString(buildConfig()).getBytes(StandardCharsets.UTF_8);
                    reset = true;
                }
                if (reset) {
                    Stat setData = client.setData().forPath(discoveryPath, data);
                    if (null == setData) {
                        logger.warn("DiscoveryHandler deal zookeeper setData failed");
                    }
                }
            }
        } catch (Throwable t) {
            logger.warn("DiscoveryHandler deal zookeeper failed ", t);
        }
    }

    private ServerSetSdConfig buildConfig() {
        ServerSetSdConfig config = new ServerSetSdConfig();
        ServiceEndpoint endpoint = new ServiceEndpoint(ipv4, monetMonitorConfigInfo.getServerPort());
        config.setServiceEndpoint(endpoint);
        Map<String, ServiceEndpoint> additionalEndpoints = new LinkedHashMap<>();
        additionalEndpoints.put(buildEndPointKey(), new ServiceEndpoint(ipv4, monetMonitorConfigInfo.getServerPort()));
        config.setAdditionalEndpoints(additionalEndpoints);
        config.setStatus("ALIVE");
        return config;
    }

    private String buildEndPointKey() {
        return monetMonitorConfigInfo.getApplicationName() + ":" + ipv4 + monetMonitorConfigInfo.getServerPort();
    }


}


