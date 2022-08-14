package com.fakebilly.monet.mq.enums;

import cn.hutool.core.util.StrUtil;

/**
 * SerializeTypeEnum
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public enum SerializeTypeEnum {

    /**
     * JDK序列化
     */
    JDK_SERIALIZE("jdk_serialize", "JDK序列化"),
    /**
     * JSON序列化
     */
    JSON_SERIALIZE("json_serialize", "JSON序列化"),

    ;

    private String code;

    private String desc;

    SerializeTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static SerializeTypeEnum from(String value) {
        if (StrUtil.isBlank(value)) {
            return null;
        }
        for (SerializeTypeEnum c : SerializeTypeEnum.values()) {
            if (c.code.equals(value)) {
                return c;
            }
        }
        return null;
    }
}
