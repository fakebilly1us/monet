package com.fakebilly.monet.core.emuns;

/**
 * CommonCode
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public enum CommonCode implements CodeEnumBase {

    /**
     * 成功
     */
    SUCCESS("000000", "成功"),
    /**
     * 错误
     */
    ERROR("999999", "抱歉，系统不太给力，请稍后再试"),

    /**
     * 参数异常
     */
    INVALID_PARAMETER("000100", "参数异常"),

    ;

    private final String errCode;
    private final String errMessage;

    CommonCode(String errCode, String errMessage) {
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
