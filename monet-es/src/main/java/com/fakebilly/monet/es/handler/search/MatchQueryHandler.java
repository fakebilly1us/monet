package com.fakebilly.monet.es.handler.search;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.fakebilly.monet.es.handler.QueryBaseHandler;
import com.fakebilly.monet.es.query.ESMatchQuery;
import com.fakebilly.monet.es.query.ESQuery;
import org.springframework.stereotype.Component;

/**
 * MatchQueryHandler
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Component("MATCH_QUERY_HANDLER")
public class MatchQueryHandler extends QueryBaseHandler {

    @Override
    public Query buildQuery(ESQuery esQuery) {
        if (null == esQuery) {
            return null;
        }
        if (!(esQuery instanceof ESMatchQuery)) {
            return null;
        }
        ESMatchQuery matchQuery = (ESMatchQuery) esQuery;
        return matchQuery.buildQuery()._toQuery();
    }


}
