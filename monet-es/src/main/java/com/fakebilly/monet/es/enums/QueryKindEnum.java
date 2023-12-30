package com.fakebilly.monet.es.enums;

/**
 * QueryKindEnum
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public enum QueryKindEnum {

    /**
     * bool
     */
    BOOL("bool", "BOOL_QUERY_HANDLER"),

    /**
     * term
     */
    TERM("term", "TERM_QUERY_HANDLER"),

    /**
     * terms
     */
    TERMS("terms", "TERMS_QUERY_HANDLER"),

    /**
     * range
     */
    RANGE("range", "RANGE_QUERY_HANDLER"),

    /**
     * match
     */
    MATCH("match", "MATCH_QUERY_HANDLER"),

    /**
     * match_all
     */
    MATCH_ALL("match_all", "MATCH_ALL_QUERY_HANDLER"),

    /**
     * match_phrase
     */
    MATCH_PHRASE("match_phrase", "MATCH_PHRASE_QUERY_HANDLER"),

    /**
     * multi_match
     */
    MULTI_MATCH("multi_match", "MULTI_MATCH_QUERY_HANDLER"),

    ;

    private String kind;
    private String handler;

    public String getKind() {
        return kind;
    }

    public String getHandler() {
        return handler;
    }

    QueryKindEnum(String kind, String handler) {
        this.kind = kind;
        this.handler = handler;
    }

}
