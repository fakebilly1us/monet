package com.fakebilly.monet.es.query;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;

import java.util.Objects;

/**
 * ESMatchAllQuery
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class ESMatchAllQuery extends ESQuery {

    private Float boost;

    private String queryName;

    public static ESMatchAllQuery create() {
        return new ESMatchAllQuery();
    }

    public ESMatchAllQuery boost(Float boost) {
        this.boost = boost;
        return this;
    }

    public ESMatchAllQuery queryName(String queryName) {
        this.queryName = queryName;
        return this;
    }

    public ESMatchAllQuery build() {
        ESMatchAllQuery maQuery = new ESMatchAllQuery();
        maQuery.boost = boost;
        maQuery.queryName = queryName;
        return maQuery;
    }

    public MatchAllQuery buildQuery() {
        return MatchAllQuery.of(ma ->
                ma.boost(boost)
                        .queryName(queryName)
        );
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
        return "ESMatchAllQuery{" +
                "boost=" + boost +
                ", queryName='" + queryName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ESMatchAllQuery)) {
            return false;
        }
        ESMatchAllQuery that = (ESMatchAllQuery) o;
        return Objects.equals(getBoost(), that.getBoost()) &&
                Objects.equals(getQueryName(), that.getQueryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBoost(), getQueryName());
    }
}
