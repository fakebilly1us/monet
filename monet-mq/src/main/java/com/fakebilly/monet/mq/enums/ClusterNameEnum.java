package com.fakebilly.monet.mq.enums;

import cn.hutool.core.util.StrUtil;

/**
 * ClusterNameEnum
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public enum ClusterNameEnum {

    /**
     * 核心集群
     */
    CENTER_CLUSTER("center", "核心集群"),

    ;

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

    private String code;
    private String desc;

    ClusterNameEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ClusterNameEnum from(String value) {
        if (StrUtil.isBlank(value)) {
            return null;
        }
        for (ClusterNameEnum c : ClusterNameEnum.values()) {
            if (c.code.equals(value)) {
                return c;
            }
        }
        return null;
    }
}
