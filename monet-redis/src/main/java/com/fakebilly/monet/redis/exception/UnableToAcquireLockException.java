package com.fakebilly.monet.redis.exception;

/**
 * UnableToAcquireLockException
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class UnableToAcquireLockException extends Exception {

    public UnableToAcquireLockException() {
        super();
    }

    public UnableToAcquireLockException(String message) {
        super(message);
    }

    public UnableToAcquireLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnableToAcquireLockException(Throwable cause) {
        super(cause);
    }

    protected UnableToAcquireLockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
