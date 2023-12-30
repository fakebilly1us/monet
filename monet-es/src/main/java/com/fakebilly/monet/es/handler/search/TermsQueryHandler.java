package com.fakebilly.monet.es.handler.search;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.fakebilly.monet.es.handler.QueryBaseHandler;
import com.fakebilly.monet.es.query.ESQuery;
import com.fakebilly.monet.es.query.ESTermsQuery;
import org.springframework.stereotype.Component;

/**
 * TermsQueryHandler
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Component("TERMS_QUERY_HANDLER")
public class TermsQueryHandler extends QueryBaseHandler {


    @Override
    public Query buildQuery(ESQuery esQuery) {
        if (null == esQuery) {
            return null;
        }
        if (!(esQuery instanceof ESTermsQuery)) {
            return null;
        }
        ESTermsQuery termsQuery = (ESTermsQuery) esQuery;
        return termsQuery.buildQuery()._toQuery();
    }


}
