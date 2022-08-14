package com.fakebilly.monet.prometheus.handler.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import javax.annotation.PreDestroy;

class DiscoveryZookeeperClient {

    private static final int RETRY_POLICY_BASE_SLEEP_TIME_MS = 1000;
    private static final int RETRY_POLICY_MAX_RETRIES = 3;

    private static final int SESSION_TIMEOUT_MS = 30000;
    private static final int CONNECTION_TIMEOUT_MS = 30000;

    private static volatile boolean start = false;
    private static CuratorFramework client;

    public static CuratorFramework getClient(String zookeeperAddress) {
        if (null == client) {
            synchronized (DiscoveryZookeeperClient.class) {
                if (null == client) {
                    init(zookeeperAddress);
                }
            }
        }
        return client;
    }

    public static CuratorFramework getClientThenStart(String zookeeperAddress) {
        if (null == client) {
            synchronized (DiscoveryZookeeperClient.class) {
                if (null == client) {
                    init(zookeeperAddress);
                    start();
                }
            }
        }
        return client;
    }

    private static void init(String zookeeperAddress) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(RETRY_POLICY_BASE_SLEEP_TIME_MS, RETRY_POLICY_MAX_RETRIES);
        client = CuratorFrameworkFactory.builder()
                .connectString(zookeeperAddress)
                .retryPolicy(retryPolicy)
                .sessionTimeoutMs(SESSION_TIMEOUT_MS)
                .connectionTimeoutMs(CONNECTION_TIMEOUT_MS)
                .build();
    }

    public static void start() {
        if (!start && null != client) {
            client.start();
            start = true;
        }
    }

    public static void stop() {
        if (null != client) {
            client.close();
            start = false;
        }
    }

    @PreDestroy
    public void destroy() {
        stop();
    }


}