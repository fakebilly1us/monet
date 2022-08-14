package com.fakebilly.monet.es.query;

import co.elastic.clients.elasticsearch._types.query_dsl.MultiMatchQuery;
import com.fakebilly.monet.es.enums.ESOperatorEnum;
import com.fakebilly.monet.es.enums.ESTextQueryTypeEnum;
import com.fakebilly.monet.es.enums.ESZeroTermsQueryEnum;

import java.util.List;
import java.util.Objects;

/**
 * ESMultiMatchQuery
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class ESMultiMatchQuery extends ESQuery {

    private List<String> fields;

    private String query;

    private String analyzer;

    private Boolean autoGenerateSynonymsPhraseQuery;

    private Double cutoffFrequency;

    private String fuzziness;

    private String fuzzyRewrite;

    private Boolean fuzzyTranspositions;

    private Boolean lenient;

    private Integer maxExpansions;

    private String minimumShouldMatch;

    private ESOperatorEnum operator;

    private Integer prefixLength;

    private Integer slop;

    private Double tieBreaker;

    private ESTextQueryTypeEnum type;

    private ESZeroTermsQueryEnum zeroTermsQuery;

    private Float boost;

    private String queryName;

    public static ESMultiMatchQuery create() {
        return new ESMultiMatchQuery();
    }

    public ESMultiMatchQuery fields(List<String> fields) {
        this.fields = fields;
        return this;
    }

    public ESMultiMatchQuery query(String query) {
        this.query = query;
        return this;
    }

    public ESMultiMatchQuery analyzer(String analyzer) {
        this.analyzer = analyzer;
        return this;
    }

    public ESMultiMatchQuery autoGenerateSynonymsPhraseQuery(Boolean autoGenerateSynonymsPhraseQuery) {
        this.autoGenerateSynonymsPhraseQuery = autoGenerateSynonymsPhraseQuery;
        return this;
    }

    public ESMultiMatchQuery cutoffFrequency(Double cutoffFrequency) {
        this.cutoffFrequency = cutoffFrequency;
        return this;
    }

    public ESMultiMatchQuery fuzziness(String fuzziness) {
        this.fuzziness = fuzziness;
        return this;
    }

    public ESMultiMatchQuery fuzzyRewrite(String fuzzyRewrite) {
        this.fuzzyRewrite = fuzzyRewrite;
        return this;
    }

    public ESMultiMatchQuery fuzzyTranspositions(Boolean fuzzyTranspositions) {
        this.fuzzyTranspositions = fuzzyTranspositions;
        return this;
    }

    public ESMultiMatchQuery lenient(Boolean lenient) {
        this.lenient = lenient;
        return this;
    }

    public ESMultiMatchQuery maxExpansions(Integer maxExpansions) {
        this.maxExpansions = maxExpansions;
        return this;
    }

    public ESMultiMatchQuery minimumShouldMatch(String minimumShouldMatch) {
        this.minimumShouldMatch = minimumShouldMatch;
        return this;
    }

    public ESMultiMatchQuery operator(ESOperatorEnum operator) {
        this.operator = operator;
        return this;
    }

    public ESMultiMatchQuery prefixLength(Integer prefixLength) {
        this.prefixLength = prefixLength;
        return this;
    }

    public ESMultiMatchQuery slop(Integer slop) {
        this.slop = slop;
        return this;
    }

    public ESMultiMatchQuery tieBreaker(Double tieBreaker) {
        this.tieBreaker = tieBreaker;
        return this;
    }

    public ESMultiMatchQuery type(ESTextQueryTypeEnum type) {
        this.type = type;
        return this;
    }

    public ESMultiMatchQuery zeroTermsQuery(ESZeroTermsQueryEnum zeroTermsQuery) {
        this.zeroTermsQuery = zeroTermsQuery;
        return this;
    }

    public ESMultiMatchQuery boost(Float boost) {
        this.boost = boost;
        return this;
    }

    public ESMultiMatchQuery queryName(String queryName) {
        this.queryName = queryName;
        return this;
    }


    public ESMultiMatchQuery build() {
        ESMultiMatchQuery mmQuery = new ESMultiMatchQuery();
        mmQuery.fields = fields;
        mmQuery.query = query;
        mmQuery.analyzer = analyzer;
        mmQuery.autoGenerateSynonymsPhraseQuery = autoGenerateSynonymsPhraseQuery;
        mmQuery.cutoffFrequency = cutoffFrequency;
        mmQuery.fuzziness = fuzziness;
        mmQuery.fuzzyRewrite = fuzzyRewrite;
        mmQuery.fuzzyTranspositions = fuzzyTranspositions;
        mmQuery.lenient = lenient;
        mmQuery.maxExpansions = maxExpansions;
        mmQuery.minimumShouldMatch = minimumShouldMatch;
        mmQuery.operator = operator;
        mmQuery.prefixLength = prefixLength;
        mmQuery.slop = slop;
        mmQuery.tieBreaker = tieBreaker;
        mmQuery.type = type;
        mmQuery.zeroTermsQuery = zeroTermsQuery;
        mmQuery.boost = boost;
        mmQuery.queryName = queryName;
        return mmQuery;
    }

    public MultiMatchQuery buildQuery() {
        return MultiMatchQuery.of(mm ->
                mm.fields(fields)
                        .query(query)
                        .analyzer(analyzer)
                        .autoGenerateSynonymsPhraseQuery(autoGenerateSynonymsPhraseQuery)
                        .cutoffFrequency(cutoffFrequency)
                        .fuzziness(fuzziness)
                        .fuzzyRewrite(fuzzyRewrite)
                        .fuzzyTranspositions(fuzzyTranspositions)
                        .lenient(lenient)
                        .maxExpansions(maxExpansions)
                        .minimumShouldMatch(minimumShouldMatch)
                        .operator(ESOperatorEnum.toOperator(operator))
                        .prefixLength(prefixLength)
                        .slop(slop)
                        .tieBreaker(tieBreaker)
                        .type(ESTextQueryTypeEnum.toTextQueryType(type))
                        .zeroTermsQuery(ESZeroTermsQueryEnum.toZeroTermsQuery(zeroTermsQuery))
                        .boost(boost)
                        .queryName(queryName)
        );
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
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

    public Boolean getAutoGenerateSynonymsPhraseQuery() {
        return autoGenerateSynonymsPhraseQuery;
    }

    public void setAutoGenerateSynonymsPhraseQuery(Boolean autoGenerateSynonymsPhraseQuery) {
        this.autoGenerateSynonymsPhraseQuery = autoGenerateSynonymsPhraseQuery;
    }

    public Double getCutoffFrequency() {
        return cutoffFrequency;
    }

    public void setCutoffFrequency(Double cutoffFrequency) {
        this.cutoffFrequency = cutoffFrequency;
    }

    public String getFuzziness() {
        return fuzziness;
    }

    public void setFuzziness(String fuzziness) {
        this.fuzziness = fuzziness;
    }

    public String getFuzzyRewrite() {
        return fuzzyRewrite;
    }

    public void setFuzzyRewrite(String fuzzyRewrite) {
        this.fuzzyRewrite = fuzzyRewrite;
    }

    public Boolean getFuzzyTranspositions() {
        return fuzzyTranspositions;
    }

    public void setFuzzyTranspositions(Boolean fuzzyTranspositions) {
        this.fuzzyTranspositions = fuzzyTranspositions;
    }

    public Boolean getLenient() {
        return lenient;
    }

    public void setLenient(Boolean lenient) {
        this.lenient = lenient;
    }

    public Integer getMaxExpansions() {
        return maxExpansions;
    }

    public void setMaxExpansions(Integer maxExpansions) {
        this.maxExpansions = maxExpansions;
    }

    public String getMinimumShouldMatch() {
        return minimumShouldMatch;
    }

    public void setMinimumShouldMatch(String minimumShouldMatch) {
        this.minimumShouldMatch = minimumShouldMatch;
    }

    public ESOperatorEnum getOperator() {
        return operator;
    }

    public void setOperator(ESOperatorEnum operator) {
        this.operator = operator;
    }

    public Integer getPrefixLength() {
        return prefixLength;
    }

    public void setPrefixLength(Integer prefixLength) {
        this.prefixLength = prefixLength;
    }

    public Integer getSlop() {
        return slop;
    }

    public void setSlop(Integer slop) {
        this.slop = slop;
    }

    public Double getTieBreaker() {
        return tieBreaker;
    }

    public void setTieBreaker(Double tieBreaker) {
        this.tieBreaker = tieBreaker;
    }

    public ESTextQueryTypeEnum getType() {
        return type;
    }

    public void setType(ESTextQueryTypeEnum type) {
        this.type = type;
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
        return "ESMultiMatchQuery{" +
                "fields=" + fields +
                ", query='" + query + '\'' +
                ", analyzer='" + analyzer + '\'' +
                ", autoGenerateSynonymsPhraseQuery=" + autoGenerateSynonymsPhraseQuery +
                ", cutoffFrequency=" + cutoffFrequency +
                ", fuzziness='" + fuzziness + '\'' +
                ", fuzzyRewrite='" + fuzzyRewrite + '\'' +
                ", fuzzyTranspositions=" + fuzzyTranspositions +
                ", lenient=" + lenient +
                ", maxExpansions=" + maxExpansions +
                ", minimumShouldMatch='" + minimumShouldMatch + '\'' +
                ", operator=" + operator +
                ", prefixLength=" + prefixLength +
                ", slop=" + slop +
                ", tieBreaker=" + tieBreaker +
                ", type=" + type +
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
        if (!(o instanceof ESMultiMatchQuery)) {
            return false;
        }
        ESMultiMatchQuery that = (ESMultiMatchQuery) o;
        return Objects.equals(getFields(), that.getFields()) &&
                Objects.equals(getQuery(), that.getQuery()) &&
                Objects.equals(getAnalyzer(), that.getAnalyzer()) &&
                Objects.equals(getAutoGenerateSynonymsPhraseQuery(), that.getAutoGenerateSynonymsPhraseQuery()) &&
                Objects.equals(getCutoffFrequency(), that.getCutoffFrequency()) &&
                Objects.equals(getFuzziness(), that.getFuzziness()) &&
                Objects.equals(getFuzzyRewrite(), that.getFuzzyRewrite()) &&
                Objects.equals(getFuzzyTranspositions(), that.getFuzzyTranspositions()) &&
                Objects.equals(getLenient(), that.getLenient()) &&
                Objects.equals(getMaxExpansions(), that.getMaxExpansions()) &&
                Objects.equals(getMinimumShouldMatch(), that.getMinimumShouldMatch()) &&
                getOperator() == that.getOperator() &&
                Objects.equals(getPrefixLength(), that.getPrefixLength()) &&
                Objects.equals(getSlop(), that.getSlop()) &&
                Objects.equals(getTieBreaker(), that.getTieBreaker()) &&
                getType() == that.getType() &&
                getZeroTermsQuery() == that.getZeroTermsQuery() &&
                Objects.equals(getBoost(), that.getBoost()) &&
                Objects.equals(getQueryName(), that.getQueryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFields(), getQuery(), getAnalyzer(), getAutoGenerateSynonymsPhraseQuery(), getCutoffFrequency(), getFuzziness(), getFuzzyRewrite(), getFuzzyTranspositions(), getLenient(), getMaxExpansions(), getMinimumShouldMatch(), getOperator(), getPrefixLength(), getSlop(), getTieBreaker(), getType(), getZeroTermsQuery(), getBoost(), getQueryName());
    }
}
