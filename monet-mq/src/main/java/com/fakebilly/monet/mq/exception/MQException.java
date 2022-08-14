package com.fakebilly.monet.mq.exception;

/**
 * MQException
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class MQException extends RuntimeException {

    private static final long serialVersionUID = 7617429156068235158L;

    public MQException() {
        super();
    }

    public MQException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public MQException(String message, Throwable cause) {
        super(message, cause);
    }

    public MQException(String message) {
        super(message);
    }

    public MQException(Throwable cause) {
        super(cause);
    }


}
