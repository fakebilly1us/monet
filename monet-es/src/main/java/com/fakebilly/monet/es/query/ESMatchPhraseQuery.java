package com.fakebilly.monet.es.query;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchPhraseQuery;
import com.fakebilly.monet.es.enums.ESZeroTermsQueryEnum;

import java.util.Objects;

/**
 * ESMatchPhraseQuery
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class ESMatchPhraseQuery extends ESQuery {

    private String field;

    private String query;

    private String analyzer;

    private Integer slop;

    private ESZeroTermsQueryEnum zeroTermsQuery;

    private Float boost;

    private String queryName;

    public static ESMatchPhraseQuery create() {
        return new ESMatchPhraseQuery();
    }

    public ESMatchPhraseQuery field(String field) {
        this.field = field;
        return this;
    }

    public ESMatchPhraseQuery query(String query) {
        this.query = query;
        return this;
    }

    public ESMatchPhraseQuery analyzer(String analyzer) {
        this.analyzer = analyzer;
        return this;
    }

    public ESMatchPhraseQuery slop(Integer slop) {
        this.slop = slop;
        return this;
    }

    public ESMatchPhraseQuery zeroTermsQuery(ESZeroTermsQueryEnum zeroTermsQuery) {
        this.zeroTermsQuery = zeroTermsQuery;
        return this;
    }

    public ESMatchPhraseQuery boost(Float boost) {
        this.boost = boost;
        return this;
    }

    public ESMatchPhraseQuery queryName(String queryName) {
        this.queryName = queryName;
        return this;
    }


    public ESMatchPhraseQuery build() {
        ESMatchPhraseQuery mpQuery = new ESMatchPhraseQuery();
        mpQuery.field = field;
        mpQuery.query = query;
        mpQuery.analyzer = analyzer;
        mpQuery.slop = slop;
        mpQuery.zeroTermsQuery = zeroTermsQuery;
        mpQuery.boost = boost;
        mpQuery.queryName = queryName;
        return mpQuery;
    }

    public MatchPhraseQuery buildQuery() {
        return MatchPhraseQuery.of(mp ->
                mp.field(field)
                        .query(query)
                        .analyzer(analyzer)
                        .slop(slop)
                        .zeroTermsQuery(ESZeroTermsQueryEnum.toZeroTermsQuery(zeroTermsQuery))
                        .boost(boost)
                        .queryName(queryName)
        );
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(String analyzer) {
        this.analyzer = analyzer;
    }

    public Integer getSlop() {
        return slop;
    }

    public void setSlop(Integer slop) {
        this.slop = slop;
    }

    public ESZeroTermsQueryEnum getZeroTermsQuery() {
        return zeroTermsQuery;
    }

    public void setZeroTermsQuery(ESZeroTermsQueryEnum zeroTermsQuery) {
        this.zeroTermsQuery = zeroTermsQuery;
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
        return "ESMatchPhraseQuery{" +
                "field='" + field + '\'' +
                ", query='" + query + '\'' +
                ", analyzer='" + analyzer + '\'' +
                ", slop=" + slop +
                ", zeroTermsQuery=" + zeroTermsQuery +
                ", boost=" + boost +
                ", queryName='" + queryName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ESMatchPhraseQuery)) {
            return false;
        }

        ESMatchPhraseQuery that = (ESMatchPhraseQuery) o;
        return Objects.equals(getField(), that.getField()) &&
                Objects.equals(getQuery(), that.getQuery()) &&
                Objects.equals(getAnalyzer(), that.getAnalyzer()) &&
                Objects.equals(getSlop(), that.getSlop()) &&
                getZeroTermsQuery() == that.getZeroTermsQuery() &&
                Objects.equals(getBoost(), that.getBoost()) &&
                Objects.equals(getQueryName(), that.getQueryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getField(), getQuery(), getAnalyzer(), getSlop(), getZeroTermsQuery(), getBoost(), getQueryName());
    }
}
