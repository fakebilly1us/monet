package com.fakebilly.monet.es.handler.search;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.fakebilly.monet.es.handler.QueryBaseHandler;
import com.fakebilly.monet.es.query.ESMatchPhraseQuery;
import com.fakebilly.monet.es.query.ESQuery;
import org.springframework.stereotype.Component;

/**
 * MatchPhraseQueryHandler
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Component("MATCH_PHRASE_QUERY_HANDLER")
public class MatchPhraseQueryHandler extends QueryBaseHandler {

    @Override
    public Query buildQuery(ESQuery esQuery) {
        if (null == esQuery) {
            return null;
        }
        if (!(esQuery instanceof ESMatchPhraseQuery)) {
            return null;
        }
        ESMatchPhraseQuery matchPhraseQuery = (ESMatchPhraseQuery) esQuery;
        return matchPhraseQuery.buildQuery()._toQuery();
    }


}
