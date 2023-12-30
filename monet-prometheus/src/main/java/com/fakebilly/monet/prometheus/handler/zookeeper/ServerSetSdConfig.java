package com.fakebilly.monet.prometheus.handler.zookeeper;

import java.util.Map;

/**
 * ServerSetSdConfig
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
class ServerSetSdConfig {

    private ServiceEndpoint serviceEndpoint;

    private Map<String, ServiceEndpoint> additionalEndpoints;

    private String status;

    private int shard;

    public ServiceEndpoint getServiceEndpoint() {
        return serviceEndpoint;
    }

    public void setServiceEndpoint(ServiceEndpoint serviceEndpoint) {
        this.serviceEndpoint = serviceEndpoint;
    }

    public Map<String, ServiceEndpoint> getAdditionalEndpoints() {
        return additionalEndpoints;
    }

    public void setAdditionalEndpoints(Map<String, ServiceEndpoint> additionalEndpoints) {
        this.additionalEndpoints = additionalEndpoints;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getShard() {
        return shard;
    }

    public void setShard(int shard) {
        this.shard = shard;
    }

    @Override
    public String toString() {
        return "ServerSetSdConfig{" +
                "serviceEndpoint=" + serviceEndpoint +
                ", additionalEndpoints=" + additionalEndpoints +
                ", status='" + status + '\'' +
                ", shard=" + shard +
                '}';
    }


}