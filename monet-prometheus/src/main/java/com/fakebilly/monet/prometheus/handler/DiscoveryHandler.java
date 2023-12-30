package com.fakebilly.monet.prometheus.handler;

/**
 * DiscoveryHandler
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public interface DiscoveryHandler {

    /**
     * getType
     * @return
     */
    String getType();

    /**
     * deal
     */
    void discovery();

}
