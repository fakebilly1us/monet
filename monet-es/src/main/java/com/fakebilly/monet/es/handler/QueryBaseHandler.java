package com.fakebilly.monet.es.handler;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.fakebilly.monet.es.enums.QueryKindEnum;
import com.fakebilly.monet.es.query.ESQuery;
import com.fakebilly.monet.es.utils.ESApplicationUtil;

/**
 * QueryBaseHandler
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public abstract class QueryBaseHandler {

    public static QueryBaseHandler getHandler(QueryKindEnum kindEnum) {
        if (null == kindEnum) {
            return null;
        }
        return (QueryBaseHandler) ESApplicationUtil.getBean(kindEnum.getHandler());
    }

    public Query buildQuery(ESQuery esQuery) {
        return null;
    }


}
