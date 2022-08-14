package com.fakebilly.monet.es.enums;

import co.elastic.clients.elasticsearch._types.query_dsl.ZeroTermsQuery;

/**
 * FieldClassEnum
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public enum ESZeroTermsQueryEnum {

    ALL,

    NONE,

    ;

    public static ZeroTermsQuery toZeroTermsQuery(ESZeroTermsQueryEnum esZeroTermsQueryEnum) {
        if (null == esZeroTermsQueryEnum) {
            return null;
        }
        switch (esZeroTermsQueryEnum) {
            case ALL:
                return ZeroTermsQuery.All;
            case NONE:
                return ZeroTermsQuery.None;
            default:
                return null;
        }

    }
}
