package com.fakebilly.monet.es.enums;

import co.elastic.clients.elasticsearch._types.query_dsl.Operator;

/**
 * FieldClassEnum
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public enum ESOperatorEnum {

    AND,

    OR,

    ;

    public static Operator toOperator(ESOperatorEnum esOperatorEnum) {
        if (null == esOperatorEnum) {
            return null;
        }
        switch (esOperatorEnum) {
            case AND:
                return Operator.And;
            case OR:
                return Operator.Or;
            default:
                return null;
        }

    }
}
