package com.fakebilly.monet.es.enums;

import co.elastic.clients.elasticsearch._types.query_dsl.RangeRelation;

/**
 * ESRangeRelationEnum
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public enum ESRangeRelationEnum {

    WITHIN,

    CONTAINS,

    INTERSECTS,

    ;

    public static RangeRelation toRangeRelation(ESRangeRelationEnum esRangeRelationEnum) {
        if (null == esRangeRelationEnum) {
            return null;
        }
        switch (esRangeRelationEnum) {
            case WITHIN:
                return RangeRelation.Within;
            case CONTAINS:
                return RangeRelation.Contains;
            case INTERSECTS:
                return RangeRelation.Intersects;
            default:
                return null;
        }

    }
}
