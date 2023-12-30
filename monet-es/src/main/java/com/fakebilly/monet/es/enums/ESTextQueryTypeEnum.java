package com.fakebilly.monet.es.enums;

import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;

/**
 * FieldClassEnum
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public enum ESTextQueryTypeEnum {

    BEST_FIELDS,
    MOST_FIELDS,
    CROSS_FIELDS,
    PHRASE,
    PHRASE_PREFIX,
    BOOL_PREFIX,

    ;

    public static TextQueryType toTextQueryType(ESTextQueryTypeEnum esTextQueryTypeEnum) {
        if (null == esTextQueryTypeEnum) {
            return null;
        }
        switch (esTextQueryTypeEnum) {
            case BEST_FIELDS:
                return TextQueryType.BestFields;
            case MOST_FIELDS:
                return TextQueryType.MostFields;
            case CROSS_FIELDS:
                return TextQueryType.CrossFields;
            case PHRASE:
                return TextQueryType.Phrase;
            case PHRASE_PREFIX:
                return TextQueryType.PhrasePrefix;
            case BOOL_PREFIX:
                return TextQueryType.BoolPrefix;
            default:
                return null;
        }

    }
}
