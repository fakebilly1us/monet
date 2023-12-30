package com.fakebilly.monet.redis.worker;

import com.fakebilly.monet.redis.exception.RedissonException;

/**
 * AcquiredLockWorker
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public interface AcquiredLockWorker<T> {

    /**
     * invokeAfterLockAcquire
     * @return
     * @throws Exception
     */
    T invokeAfterLockAcquire() throws RedissonException;


}
