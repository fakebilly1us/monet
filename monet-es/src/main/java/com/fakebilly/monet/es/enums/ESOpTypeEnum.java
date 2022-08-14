package com.fakebilly.monet.es.enums;

import co.elastic.clients.elasticsearch._types.OpType;

/**
 * FieldClassEnum
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public enum ESOpTypeEnum {

    INDEX,

    CREATE,

    ;

    public static OpType toOpType(ESOpTypeEnum esOpTypeEnum) {
        if (null == esOpTypeEnum) {
            return null;
        }
        switch (esOpTypeEnum) {
            case INDEX:
                return OpType.Index;
            case CREATE:
                return OpType.Create;
            default:
                return null;
        }

    }
}
