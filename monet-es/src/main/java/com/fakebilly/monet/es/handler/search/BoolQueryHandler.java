package com.fakebilly.monet.es.handler.search;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.fakebilly.monet.es.handler.QueryBaseHandler;
import com.fakebilly.monet.es.query.ESBoolQuery;
import com.fakebilly.monet.es.query.ESQuery;
import org.springframework.stereotype.Component;

/**
 * BoolQueryHandler
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Component("BOOL_QUERY_HANDLER")
public class BoolQueryHandler extends QueryBaseHandler {

    @Override
    public Query buildQuery(ESQuery esQuery) {
        if (null == esQuery) {
            return null;
        }
        if (!(esQuery instanceof ESBoolQuery)) {
            return null;
        }
        ESBoolQuery boolQuery = (ESBoolQuery) esQuery;
        return boolQuery.buildQuery()._toQuery();
    }


}
