package com.fakebilly.monet.redis.exception;

/**
 * RedissonException
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class RedissonException extends Exception {

    public RedissonException() {
        super();
    }

    public RedissonException(String message) {
        super(message);
    }

    public RedissonException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedissonException(Throwable cause) {
        super(cause);
    }

    protected RedissonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
