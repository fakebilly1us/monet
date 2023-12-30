package com.fakebilly.monet.es.handler.search;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.fakebilly.monet.es.handler.QueryBaseHandler;
import com.fakebilly.monet.es.query.ESQuery;
import com.fakebilly.monet.es.query.ESRangeQuery;
import org.springframework.stereotype.Component;

/**
 * RangeQueryHandler
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Component("RANGE_QUERY_HANDLER")
public class RangeQueryHandler extends QueryBaseHandler {

    @Override
    public Query buildQuery(ESQuery esQuery) {
        if (null == esQuery) {
            return null;
        }
        if (!(esQuery instanceof ESRangeQuery)) {
            return null;
        }
        ESRangeQuery rangeQuery = (ESRangeQuery) esQuery;
        return rangeQuery.buildQuery()._toQuery();
    }


}
