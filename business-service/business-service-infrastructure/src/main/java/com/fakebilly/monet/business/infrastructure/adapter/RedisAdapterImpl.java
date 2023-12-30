package com.fakebilly.monet.business.infrastructure.adapter;

import com.fakebilly.monet.business.domain.adapter.RedisAdapter;
import com.fakebilly.monet.redis.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * RedisAdapter.RedisAdapterImpl
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Component
public class RedisAdapterImpl implements RedisAdapter {

    @Autowired
    private IRedisService redisService;

    @Override
    public void set(String key, Serializable value, long timeToLiveInSeconds) {
        redisService.set(key, value, timeToLiveInSeconds);
    }

    @Override
    public <T extends Serializable> T get(String key) {
        return redisService.get(key);
    }

    @Override
    public boolean lock(String lockKey, long lockTime, long maxWaitTime) throws InterruptedException {
        return redisService.lock(lockKey, lockTime, maxWaitTime);
    }

    @Override
    public void unlock(String lockKey) {
        redisService.unlock(lockKey);
    }

    @Override
    public boolean exists(String key) {
        return redisService.exists(key);
    }
}
