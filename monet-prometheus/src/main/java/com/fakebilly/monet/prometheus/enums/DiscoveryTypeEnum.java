package com.fakebilly.monet.prometheus.enums;

import cn.hutool.core.util.StrUtil;

/**
 * DiscoveryTypeEnum
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public enum DiscoveryTypeEnum {

    /**
     * file
     */
    FILE("file", "文件"),
    /**
     * zookeeper
     */
    ZOOKEEPER("zookeeper", "zookeeper"),

    ;


    private String code;

    private String desc;

    DiscoveryTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public static DiscoveryTypeEnum getEnumByCode(String code) {
        if (StrUtil.isBlank(code)) {
            return null;
        }
        for (DiscoveryTypeEnum typeEnum : values()) {
            if (typeEnum.getCode().equals(code)) {
                return typeEnum;
            }
        }
        return null;
    }

    public static String getDesc(String code) {
        if (StrUtil.isBlank(code)) {
            return null;
        }
        DiscoveryTypeEnum enumBy = getEnumByCode(code);
        return null == enumBy ? null : enumBy.getDesc();
    }


}
