package com.fakebilly.monet.core.exception;

import com.fakebilly.monet.core.emuns.CodeEnumBase;

/**
 * BusinessException
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class BusinessException extends RuntimeException {

    private final String errCode;

    public BusinessException(CodeEnumBase error) {
        this(error.getCode(), error.getMessage());
    }

    public BusinessException(CodeEnumBase error, Throwable cause) {
        this(error.getCode(), error.getMessage(), cause);
    }

    public BusinessException(String errCode, String message) {
        super(message);
        this.errCode = errCode;
    }

    public BusinessException(String errCode, String message, Throwable cause) {
        super(message, cause);
        this.errCode = errCode;
    }

    public String getErrCode() {
        return this.errCode;
    }

}
