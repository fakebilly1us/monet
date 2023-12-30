package com.fakebilly.monet.core.exception;

import com.fakebilly.monet.core.emuns.CodeEnumBase;

/**
 * CommonExceptionCode
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public enum CommonExceptionCode implements CodeEnumBase {

    /**
     * 通用未定义异常
     */
    DEFAULT_ERROR("999998", ""),
    /**
     * 严重级别未定义异常
     */
    UNKNOWN("999999", "抱歉，系统不太给力，请稍后再试"),
    /**
     * 传入参数为空
     */
    BLANK_PARAMETER("000101", "传入参数为空"),
    /**
     * 传入参数有误
     */
    INVALID_PARAMETER("000102", "传入参数有误"),
    /**
     * 数据不存在
     */
    NOT_FOUND("000201", "通用不存在"),
    /**
     * 接口异常
     */
    TIME_OUT("000399", "接口异常"),
    /**
     * 未登录或登录已过期，请重新登录
     */
    LOGIN_EXPIRED("001001", "未登录或登录已过期，请重新登录"),


    ;

    private final String errCode;
    private final String errMessage;

    CommonExceptionCode(String errCode, String errMessage) {
        this.errCode = errCode;
        this.errMessage = errMessage;
    }

    public String getCode() {
        return this.errCode;
    }

    public String getMessage() {
        return this.errMessage;
    }
}
