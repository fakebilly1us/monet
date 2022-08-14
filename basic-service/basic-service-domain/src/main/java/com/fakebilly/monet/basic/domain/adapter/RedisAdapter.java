package com.fakebilly.monet.basic.domain.adapter;

import java.io.Serializable;

/**
 * RedisAdapter
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public interface RedisAdapter {

    /**
     * 设置缓存
     *
     * @param key
     * @param value
     * @param timeToLiveInSeconds 时间单位：秒
     */
    void set(String key, Serializable value, long timeToLiveInSeconds);

    /**
     * 获取缓存
     *
     * @param key
     * @return
     */
    <T extends Serializable> T get(String key);


    /**
     * 分布式锁
     * 加锁以后 最多等待{maxWaitTime}，上锁以后{lockTime}自动解锁
     *
     * @param lockKey
     * @param lockTime    自动解锁时间 时间单位：秒
     * @param maxWaitTime 最多等待时间 时间单位：秒
     * @return
     * @throws InterruptedException
     */
    boolean lock(String lockKey, long lockTime, long maxWaitTime) throws InterruptedException;

    /**
     * 解锁
     *
     * @param lockKey
     * @return
     */
    void unlock(String lockKey);

    /**
     * 指定的key 是否存在
     * @param key
     * @return
     */
    boolean exists(String key);
}
