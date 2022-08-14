package com.fakebilly.monet.es.enums;

import co.elastic.clients.elasticsearch._types.WaitForActiveShardOptions;

/**
 * ESWaitForActiveShardOptionsEnum
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public enum ESWaitForActiveShardOptionsEnum {

    ALL,

    ;

    public static WaitForActiveShardOptions toWaitForActiveShardOptions(ESWaitForActiveShardOptionsEnum esWait) {
        if (null == esWait) {
            return null;
        }
        switch (esWait) {
            case ALL:
                return WaitForActiveShardOptions.All;
            default:
                return null;
        }
    }

    public static ESWaitForActiveShardOptionsEnum getEnum(ESWaitForActiveShardOptionsEnum esWait) {
        if (null == esWait) {
            return null;
        }
        for (ESWaitForActiveShardOptionsEnum wait : values()) {
            if (wait == esWait) {
                return wait;
            }
        }
        return null;
    }

}
