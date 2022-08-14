package com.fakebilly.monet.es.handler.search;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.fakebilly.monet.es.handler.QueryBaseHandler;
import com.fakebilly.monet.es.query.ESQuery;
import com.fakebilly.monet.es.query.ESTermQuery;
import org.springframework.stereotype.Component;

/**
 * TermQueryHandler
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Component("TERM_QUERY_HANDLER")
public class TermQueryHandler extends QueryBaseHandler {

    @Override
    public Query buildQuery(ESQuery esQuery) {
        if (null == esQuery) {
            return null;
        }
        if (!(esQuery instanceof ESTermQuery)) {
            return null;
        }
        ESTermQuery termQuery = (ESTermQuery) esQuery;
        return termQuery.buildQuery()._toQuery();
    }


}
