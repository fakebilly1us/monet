package com.fakebilly.monet.prometheus.handler.zookeeper;

/**
 * ServiceEndpoint
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
class ServiceEndpoint {

    private String host;

    private String port;

    public ServiceEndpoint() {
    }

    public ServiceEndpoint(String host, String port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "ServiceEndpoint{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                '}';
    }
}