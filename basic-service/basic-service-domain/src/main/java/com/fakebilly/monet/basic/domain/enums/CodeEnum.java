package com.fakebilly.monet.basic.domain.enums;

/**
 * CodeEnum
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public enum CodeEnum {

    /**
     * 成功
     */
    SUCCESS("000000", "成功"),

    /**
     * 未定义异常
     */
    ERROR("999999", "未定义异常"),

    /**
     * 参数异常
     */
    INVALID_PARAMETER("000100", "参数异常"),

    /**
     * 数据项不能为空
     */
    NULL("000101", "数据项不能为空"),

    /**
     * 无效的数据
     */
    INVALID("000102", "无效的数据"),

    /**
     * 无效的数据格式
     */
    INVALID_DATA("000103", "无效的数据格式"),

    /**
     * 其他数据校验异常
     */
    ERROR_DATA("000199", "其他数据校验异常"),

    /**
     * 数据不存在
     */
    NOTEXIST("000201", "数据不存在"),

    /**
     * 数据已存在
     */
    EXIST("000202", "数据已存在"),

    /**
     * 数据异常
     */
    ERROR_DATA_PROCESS("000299", "数据异常"),

    /**
     * 远程服务调用失败
     */
    ERROR_RPC("000398", "远程服务调用失败"),
    /**
     * 其它接口异常
     */
    ERROR_RPC_PROCESS("000399", "其它接口异常"),

    /**
     * 请求响应超时
     */
    ERROR_RPC_TIMEOUT("000397", "请求响应超时"),

    /**
     * 业务逻辑异常
     */
    ERROR_BIZ_LOGIC("000499", "业务逻辑异常"),

    /**
     * 文件处理异常
     */
    ERROR_FILE_PROCESS("000599", "文件处理异常"),

    /**
     * 缓存处理异常
     */
    ERROR_CACHE_PROCESS("000699", "缓存处理异常"),

    /**
     * MQ处理异常
     */
    ERROR_MQ("000799", "MQ处理异常"),

    /**
     * 短信发送异常
     */
    ERROR_SEND_SMS("000899", "短信发送异常"),

    /**
     * 数据库处理异常
     */
    ERROR_DATABASE("000999", "数据库处理异常"),

    /**
     * 用户认证失败
     */
    AUTH_FAIL("001009", "用户认证失败"),


    ;

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    CodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
