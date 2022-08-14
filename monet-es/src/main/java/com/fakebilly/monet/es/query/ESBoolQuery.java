package com.fakebilly.monet.es.query;

import cn.hutool.core.collection.CollectionUtil;
import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ESMatchAllQuery
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class ESBoolQuery extends ESQuery {

    private List<ESQuery> filter;

    private List<ESQuery> must;

    private List<ESQuery> mustNot;

    private List<ESQuery> should;

    private String minimumShouldMatch;

    private Float boost;

    private String queryName;


    public static ESBoolQuery create() {
        return new ESBoolQuery();
    }

    public ESBoolQuery filter(List<ESQuery> filter) {
        this.filter = filter;
        return this;
    }

    public ESBoolQuery must(List<ESQuery> must) {
        this.must = must;
        return this;
    }

    public ESBoolQuery mustNot(List<ESQuery> mustNot) {
        this.mustNot = mustNot;
        return this;
    }

    public ESBoolQuery should(List<ESQuery> should) {
        this.should = should;
        return this;
    }

    public ESBoolQuery minimumShouldMatch(String minimumShouldMatch) {
        this.minimumShouldMatch = minimumShouldMatch;
        return this;
    }


    public ESBoolQuery boost(Float boost) {
        this.boost = boost;
        return this;
    }

    public ESBoolQuery queryName(String queryName) {
        this.queryName = queryName;
        return this;
    }

    public ESBoolQuery build() {
        ESBoolQuery bQuery = new ESBoolQuery();
        bQuery.filter = filter;
        bQuery.must = must;
        bQuery.mustNot = mustNot;
        bQuery.should = should;
        bQuery.minimumShouldMatch = minimumShouldMatch;
        bQuery.boost = boost;
        bQuery.queryName = queryName;
        return bQuery;
    }

    public BoolQuery buildQuery() {
        return BoolQuery.of(b ->
                b.filter(buildQueries(filter))
                        .must(buildQueries(must))
                        .mustNot(buildQueries(mustNot))
                        .should(buildQueries(should))
                        .minimumShouldMatch(minimumShouldMatch)
                        .boost(boost)
                        .queryName(queryName)
        );
    }

    private List<Query> buildQueries(List<ESQuery> esQueries) {
        if (CollectionUtil.isEmpty(esQueries)) {
            return null;
        }
        List<Query> queries = new ArrayList<>(esQueries.size());
        Query query = null;
        for (ESQuery esQuery : esQueries) {
            if (esQuery instanceof ESTermQuery) {
                ESTermQuery tQuery = (ESTermQuery) esQuery;
                query = tQuery.buildQuery()._toQuery();
            } else if (esQuery instanceof ESTermsQuery) {
                ESTermsQuery tsQuery = (ESTermsQuery) esQuery;
                query = tsQuery.buildQuery()._toQuery();
            } else if (esQuery instanceof ESRangeQuery) {
                ESRangeQuery rQuery = (ESRangeQuery) esQuery;
                query = rQuery.buildQuery()._toQuery();
            } else if (esQuery instanceof ESMultiMatchQuery) {
                ESMultiMatchQuery mmQuery = (ESMultiMatchQuery) esQuery;
                query = mmQuery.buildQuery()._toQuery();
            } else if (esQuery instanceof ESMatchQuery) {
                ESMatchQuery mQuery = (ESMatchQuery) esQuery;
                query = mQuery.buildQuery()._toQuery();
            } else if (esQuery instanceof ESMatchPhraseQuery) {
                ESMatchPhraseQuery mpQuery = (ESMatchPhraseQuery) esQuery;
                query = mpQuery.buildQuery()._toQuery();
            } else if (esQuery instanceof ESMatchAllQuery) {
                ESMatchAllQuery maQuery = (ESMatchAllQuery) esQuery;
                query = maQuery.buildQuery()._toQuery();
            } else {
                query = null;
            }
            if (null != query) {
                queries.add(query);
            }
        }
        return queries;
    }

    public List<ESQuery> getFilter() {
        return filter;
    }

    public void setFilter(List<ESQuery> filter) {
        this.filter = filter;
    }

    public List<ESQuery> getMust() {
        return must;
    }

    public void setMust(List<ESQuery> must) {
        this.must = must;
    }

    public List<ESQuery> getMustNot() {
        return mustNot;
    }

    public void setMustNot(List<ESQuery> mustNot) {
        this.mustNot = mustNot;
    }

    public List<ESQuery> getShould() {
        return should;
    }

    public void setShould(List<ESQuery> should) {
        this.should = should;
    }

    public String getMinimumShouldMatch() {
        return minimumShouldMatch;
    }

    public void setMinimumShouldMatch(String minimumShouldMatch) {
        this.minimumShouldMatch = minimumShouldMatch;
    }

    public Float getBoost() {
        return boost;
    }

    public void setBoost(Float boost) {
        this.boost = boost;
    }

    public String getQueryName() {
        return queryName;
    }

    public void setQueryName(String queryName) {
        this.queryName = queryName;
    }

    @Override
    public String toString() {
        return "ESBoolQuery{" +
                "filter=" + filter +
                ", must=" + must +
                ", mustNot=" + mustNot +
                ", should=" + should +
                ", minimumShouldMatch='" + minimumShouldMatch + '\'' +
                ", boost=" + boost +
                ", queryName='" + queryName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ESBoolQuery)) {
            return false;
        }
        ESBoolQuery that = (ESBoolQuery) o;
        return Objects.equals(filter, that.filter) &&
                Objects.equals(must, that.must) &&
                Objects.equals(mustNot, that.mustNot) &&
                Objects.equals(should, that.should) &&
                Objects.equals(minimumShouldMatch, that.minimumShouldMatch) &&
                Objects.equals(getBoost(), that.getBoost()) &&
                Objects.equals(getQueryName(), that.getQueryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(filter, must, mustNot, should, minimumShouldMatch, getBoost(), getQueryName());
    }
}
