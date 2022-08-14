package com.fakebilly.monet.prometheus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * MonetMonitorConfigInfo
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Component
public class MonetMonitorConfigInfo {

    public static final String APPLICATION_NAME_KEY = "application";

    public static String APPLICATION_NAME = "";

    @PostConstruct
    public void init() {
        APPLICATION_NAME = applicationName;
    }

    @Value("${spring.application.name:monet-prometheus-default}")
    private String applicationName;

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${monet.prometheus.discovery:true}")
    private boolean discovery;

    @Value("${monet.prometheus.discovery.type:}")
    private String discoveryType;

    @Value("${monet.prometheus.discovery.file.path:}")
    private String discoveryFilePath;

    @Value("${monet.prometheus.discovery.delay:60000}")
    private long discoveryDelay;

    @Value("${monet.prometheus.discovery.zookeeper.address:}")
    private String discoveryZookeeperAddress;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public boolean discovery() {
        return discovery;
    }

    public void setDiscovery(boolean discovery) {
        this.discovery = discovery;
    }

    public String getDiscoveryType() {
        return discoveryType;
    }

    public void setDiscoveryType(String discoveryType) {
        this.discoveryType = discoveryType;
    }

    public String getDiscoveryFilePath() {
        return discoveryFilePath;
    }

    public void setDiscoveryFilePath(String discoveryFilePath) {
        this.discoveryFilePath = discoveryFilePath;
    }

    public long getDiscoveryDelay() {
        return discoveryDelay;
    }

    public void setDiscoveryDelay(long discoveryDelay) {
        this.discoveryDelay = discoveryDelay;
    }

    public String getDiscoveryZookeeperAddress() {
        return discoveryZookeeperAddress;
    }

    public void setDiscoveryZookeeperAddress(String discoveryZookeeperAddress) {
        this.discoveryZookeeperAddress = discoveryZookeeperAddress;
    }

    @Override
    public String toString() {
        return "MonetMonitorConfigInfo{" +
                "applicationName='" + applicationName + '\'' +
                ", serverPort='" + serverPort + '\'' +
                ", discovery=" + discovery +
                ", discoveryType='" + discoveryType + '\'' +
                ", discoveryPath='" + discoveryFilePath + '\'' +
                ", discoveryDelay=" + discoveryDelay +
                ", discoveryZookeeperAddress='" + discoveryZookeeperAddress + '\'' +
                '}';
    }
}
