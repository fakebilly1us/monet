package com.fakebilly.monet.es.config;

import org.apache.http.HttpHost;

import java.util.List;

/**
 * ElasticSearchConfigInfo
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class ElasticSearchConfigInfo {

    private List<HttpHost> hosts;

    private String userName;

    private String password;

    public List<HttpHost> getHosts() {
        return hosts;
    }

    public void setHosts(List<HttpHost> hosts) {
        this.hosts = hosts;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
