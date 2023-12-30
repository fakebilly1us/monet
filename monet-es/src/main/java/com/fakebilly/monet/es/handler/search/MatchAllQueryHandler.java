package com.fakebilly.monet.es.handler.search;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.fakebilly.monet.es.handler.QueryBaseHandler;
import com.fakebilly.monet.es.query.ESMatchAllQuery;
import com.fakebilly.monet.es.query.ESQuery;
import org.springframework.stereotype.Component;

/**
 * MatchAllQueryHandler
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Component("MATCH_ALL_QUERY_HANDLER")
public class MatchAllQueryHandler extends QueryBaseHandler {

    @Override
    public Query buildQuery(ESQuery esQuery) {
        if (null == esQuery) {
            return null;
        }
        if (!(esQuery instanceof ESMatchAllQuery)) {
            return null;
        }
        ESMatchAllQuery matchAllQuery = (ESMatchAllQuery) esQuery;
        return matchAllQuery.buildQuery()._toQuery();
    }


}
