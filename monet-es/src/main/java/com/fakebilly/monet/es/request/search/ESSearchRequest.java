package com.fakebilly.monet.es.request.search;

import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import com.fakebilly.monet.es.enums.QueryKindEnum;
import com.fakebilly.monet.es.handler.QueryBaseHandler;
import com.fakebilly.monet.es.query.ESQuery;

import java.util.Objects;

/**
 * ESSearchRequest
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class ESSearchRequest {

    private String index;

    private Integer from;

    private Integer size;

    private QueryKindEnum queryKindEnum;

    private ESQuery esQuery;

    public static ESSearchRequest create() {
        return new ESSearchRequest();
    }

    protected ESSearchRequest() {
    }

    public ESSearchRequest index(String index) {
        this.index = index;
        return this;
    }

    public ESSearchRequest from(Integer from) {
        this.from = from;
        return this;
    }

    public ESSearchRequest size(Integer size) {
        this.size = size;
        return this;
    }

    public ESSearchRequest esQuery(ESQuery esQuery) {
        this.esQuery = esQuery;
        return this;
    }

    public SearchRequest toRequest() {
        queryKindEnum = null == queryKindEnum ? QueryKindEnum.BOOL : queryKindEnum;
        QueryBaseHandler handler = QueryBaseHandler.getHandler(queryKindEnum);
        if (null == handler) {
            return null;
        }
        Query targetQuery = handler.buildQuery(esQuery);
        boolean stop = StrUtil.isBlank(index) && null == targetQuery;
        if (stop) {
            return null;
        }
        return SearchRequest.of(r ->
                {
                    r.index(index).from(from).size(size);
                    if (null != targetQuery) {
                        r.query(q -> {
                            switch (queryKindEnum) {
                                case BOOL: {
                                    q.bool(targetQuery.bool());
                                }
                                break;
                                case TERM: {
                                    q.term(targetQuery.term());
                                }
                                break;
                                case TERMS: {
                                    q.terms(targetQuery.terms());
                                }
                                break;
                                case RANGE: {
                                    q.range(targetQuery.range());
                                }
                                break;
                                case MATCH: {
                                    q.match(targetQuery.match());
                                }
                                break;
                                case MATCH_ALL: {
                                    q.matchAll(targetQuery.matchAll());
                                }

                                break;
                                case MATCH_PHRASE: {
                                    q.matchPhrase(targetQuery.matchPhrase());
                                }
                                break;
                                case MULTI_MATCH: {
                                    q.multiMatch(targetQuery.multiMatch());
                                }
                                break;
                                default:
                                    break;
                            }
                            return q;
                        });
                    }
                    return r;
                }
        );
    }

    @Override
    public String toString() {
        return "ESSearchRequest{" +
                "index='" + index + '\'' +
                ", from=" + from +
                ", size=" + size +
                ", queryKindEnum=" + queryKindEnum +
                ", esQuery=" + esQuery +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ESSearchRequest)) {
            return false;
        }
        ESSearchRequest that = (ESSearchRequest) o;
        return Objects.equals(index, that.index) && Objects.equals(from, that.from) && Objects.equals(size, that.size) && queryKindEnum == that.queryKindEnum && Objects.equals(esQuery, that.esQuery);
    }

    @Override
    public int hashCode() {
        return Objects.hash(index, from, size, queryKindEnum, esQuery);
    }
}
