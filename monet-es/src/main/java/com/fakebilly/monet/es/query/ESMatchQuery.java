package com.fakebilly.monet.es.query;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import com.fakebilly.monet.es.enums.ESOperatorEnum;
import com.fakebilly.monet.es.enums.ESZeroTermsQueryEnum;
import com.fakebilly.monet.es.enums.FieldClassEnum;

import java.util.Objects;

/**
 * ESMatchQuery
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class ESMatchQuery extends ESQuery {

    private String field;

    private Object query;

    private FieldClassEnum fieldClassEnum;

    private String analyzer;

    private Boolean autoGenerateSynonymsPhraseQuery;

    private Double cutoffFrequency;

    private String fuzziness;

    private String fuzzyRewrite;

    private Boolean fuzzyTranspositions;

    private Boolean lenient;

    private Integer maxExpansions;

    private String minimumShouldMatch;

    private Integer prefixLength;

    private ESOperatorEnum operator;

    private ESZeroTermsQueryEnum zeroTermsQuery;

    private Float boost;

    private String queryName;

    public static ESMatchQuery create() {
        return new ESMatchQuery();
    }

    public ESMatchQuery field(String field) {
        this.field = field;
        return this;
    }

    public ESMatchQuery value(Object value) {
        this.query = value;
        return this;
    }

    public ESMatchQuery fieldClassEnum(FieldClassEnum fieldClassEnum) {
        this.fieldClassEnum = fieldClassEnum;
        return this;
    }

    public ESMatchQuery analyzer(String analyzer) {
        this.analyzer = analyzer;
        return this;
    }

    public ESMatchQuery autoGenerateSynonymsPhraseQuery(Boolean autoGenerateSynonymsPhraseQuery) {
        this.autoGenerateSynonymsPhraseQuery = autoGenerateSynonymsPhraseQuery;
        return this;
    }

    public ESMatchQuery cutoffFrequency(Double cutoffFrequency) {
        this.cutoffFrequency = cutoffFrequency;
        return this;
    }

    public ESMatchQuery fuzziness(String fuzziness) {
        this.fuzziness = fuzziness;
        return this;
    }

    public ESMatchQuery fuzzyRewrite(String fuzzyRewrite) {
        this.fuzzyRewrite = fuzzyRewrite;
        return this;
    }

    public ESMatchQuery fuzzyTranspositions(Boolean fuzzyTranspositions) {
        this.fuzzyTranspositions = fuzzyTranspositions;
        return this;
    }

    public ESMatchQuery lenient(Boolean lenient) {
        this.lenient = lenient;
        return this;
    }

    public ESMatchQuery maxExpansions(Integer maxExpansions) {
        this.maxExpansions = maxExpansions;
        return this;
    }

    public ESMatchQuery minimumShouldMatch(String minimumShouldMatch) {
        this.minimumShouldMatch = minimumShouldMatch;
        return this;
    }

    public ESMatchQuery prefixLength(Integer prefixLength) {
        this.prefixLength = prefixLength;
        return this;
    }

    public ESMatchQuery operator(ESOperatorEnum operator) {
        this.operator = operator;
        return this;
    }

    public ESMatchQuery zeroTermsQuery(ESZeroTermsQueryEnum zeroTermsQuery) {
        this.zeroTermsQuery = zeroTermsQuery;
        return this;
    }

    public ESMatchQuery boost(Float boost) {
        this.boost = boost;
        return this;
    }

    public ESMatchQuery queryName(String queryName) {
        this.queryName = queryName;
        return this;
    }


    public ESMatchQuery build() {
        ESMatchQuery mQuery = new ESMatchQuery();
        mQuery.field = field;
        mQuery.query = query;
        mQuery.fieldClassEnum = fieldClassEnum;
        mQuery.analyzer = analyzer;
        mQuery.autoGenerateSynonymsPhraseQuery = autoGenerateSynonymsPhraseQuery;
        mQuery.cutoffFrequency = cutoffFrequency;
        mQuery.fuzziness = fuzziness;
        mQuery.fuzzyRewrite = fuzzyRewrite;
        mQuery.fuzzyTranspositions = fuzzyTranspositions;
        mQuery.lenient = lenient;
        mQuery.maxExpansions = maxExpansions;
        mQuery.minimumShouldMatch = minimumShouldMatch;
        mQuery.prefixLength = prefixLength;
        mQuery.operator = operator;
        mQuery.zeroTermsQuery = zeroTermsQuery;
        mQuery.boost = boost;
        mQuery.queryName = queryName;
        return mQuery;
    }

    public MatchQuery buildQuery() {
        return MatchQuery.of(m ->
                m.field(field)
                        .query(buildFieldValue())
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
                        .zeroTermsQuery(ESZeroTermsQueryEnum.toZeroTermsQuery(zeroTermsQuery))
                        .boost(boost)
                        .queryName(queryName)
        );
    }

    private FieldValue buildFieldValue() {
        if (null == fieldClassEnum) {
            return FieldValue.NULL;
        }
        FieldValue fieldValue = null;
        switch (fieldClassEnum) {
            case STRING_CLASS:
                fieldValue = FieldValue.of((String) query);
                break;
            case LONG_CLASS:
                fieldValue = FieldValue.of((Long) query);
                break;
            case DOUBLE_CLASS:
                fieldValue = FieldValue.of((Double) query);
                break;
            case BOOLEAN_CLASS:
                fieldValue = FieldValue.of((Boolean) query);
                break;
            case NULL_VALUE:
            default:
                fieldValue = FieldValue.NULL;
                break;
        }
        return fieldValue;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getQuery() {
        return query;
    }

    public void setQuery(Object query) {
        this.query = query;
    }

    public FieldClassEnum getFieldClassEnum() {
        return fieldClassEnum;
    }

    public void setFieldClassEnum(FieldClassEnum fieldClassEnum) {
        this.fieldClassEnum = fieldClassEnum;
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

    public Integer getPrefixLength() {
        return prefixLength;
    }

    public void setPrefixLength(Integer prefixLength) {
        this.prefixLength = prefixLength;
    }

    public ESOperatorEnum getOperator() {
        return operator;
    }

    public void setOperator(ESOperatorEnum operator) {
        this.operator = operator;
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
        return "ESMatchQuery{" +
                "field='" + field + '\'' +
                ", query=" + query +
                ", fieldClassEnum=" + fieldClassEnum +
                ", analyzer='" + analyzer + '\'' +
                ", autoGenerateSynonymsPhraseQuery=" + autoGenerateSynonymsPhraseQuery +
                ", cutoffFrequency=" + cutoffFrequency +
                ", fuzziness='" + fuzziness + '\'' +
                ", fuzzyRewrite='" + fuzzyRewrite + '\'' +
                ", fuzzyTranspositions=" + fuzzyTranspositions +
                ", lenient=" + lenient +
                ", maxExpansions=" + maxExpansions +
                ", minimumShouldMatch='" + minimumShouldMatch + '\'' +
                ", prefixLength=" + prefixLength +
                ", operator=" + operator +
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
        if (!(o instanceof ESMatchQuery)) {
            return false;
        }
        ESMatchQuery that = (ESMatchQuery) o;
        return Objects.equals(getField(), that.getField()) &&
                Objects.equals(getQuery(), that.getQuery()) &&
                getFieldClassEnum() == that.getFieldClassEnum() &&
                Objects.equals(analyzer, that.analyzer) &&
                Objects.equals(autoGenerateSynonymsPhraseQuery, that.autoGenerateSynonymsPhraseQuery) &&
                Objects.equals(cutoffFrequency, that.cutoffFrequency) &&
                Objects.equals(fuzziness, that.fuzziness) &&
                Objects.equals(fuzzyRewrite, that.fuzzyRewrite) &&
                Objects.equals(fuzzyTranspositions, that.fuzzyTranspositions) &&
                Objects.equals(lenient, that.lenient) &&
                Objects.equals(maxExpansions, that.maxExpansions) &&
                Objects.equals(minimumShouldMatch, that.minimumShouldMatch) &&
                Objects.equals(prefixLength, that.prefixLength) &&
                operator == that.operator &&
                zeroTermsQuery == that.zeroTermsQuery &&
                Objects.equals(getBoost(), that.getBoost()) &&
                Objects.equals(getQueryName(), that.getQueryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getField(), getQuery(), getFieldClassEnum(), analyzer, autoGenerateSynonymsPhraseQuery, cutoffFrequency, fuzziness, fuzzyRewrite, fuzzyTranspositions, lenient, maxExpansions, minimumShouldMatch, prefixLength, operator, zeroTermsQuery, getBoost(), getQueryName());
    }
}
