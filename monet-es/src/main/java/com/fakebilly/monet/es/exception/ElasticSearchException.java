package com.fakebilly.monet.es.exception;

/**
 * ElasticSearchException
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class ElasticSearchException extends RuntimeException {

    public ElasticSearchException() {
        super();
    }

    public ElasticSearchException(String message) {
        super(message);
    }

    public ElasticSearchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElasticSearchException(Throwable cause) {
        super(cause);
    }

    protected ElasticSearchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
