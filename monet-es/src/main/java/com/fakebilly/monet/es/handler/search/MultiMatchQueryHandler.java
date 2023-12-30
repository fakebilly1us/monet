package com.fakebilly.monet.es.handler.search;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.fakebilly.monet.es.handler.QueryBaseHandler;
import com.fakebilly.monet.es.query.ESMultiMatchQuery;
import com.fakebilly.monet.es.query.ESQuery;
import org.springframework.stereotype.Component;

/**
 * MultiMatchQueryHandler
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Component("MULTI_MATCH_QUERY_HANDLER")
public class MultiMatchQueryHandler extends QueryBaseHandler {

    @Override
    public Query buildQuery(ESQuery esQuery) {
        if (null == esQuery) {
            return null;
        }
        if (!(esQuery instanceof ESMultiMatchQuery)) {
            return null;
        }
        ESMultiMatchQuery multiMatchQuery = (ESMultiMatchQuery) esQuery;
        return multiMatchQuery.buildQuery()._toQuery();
    }


}
