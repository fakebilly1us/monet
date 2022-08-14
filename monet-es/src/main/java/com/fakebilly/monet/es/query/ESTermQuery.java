package com.fakebilly.monet.es.query;

import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.TermQuery;
import com.fakebilly.monet.es.enums.FieldClassEnum;

import java.util.Objects;

/**
 * ESTermQuery
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class ESTermQuery extends ESQuery {

    private String field;

    private Object value;

    private FieldClassEnum fieldClassEnum;

    private Boolean caseInsensitive;

    private Float boost;

    private String queryName;

    public static ESTermQuery create() {
        return new ESTermQuery();
    }

    public ESTermQuery field(String field) {
        this.field = field;
        return this;
    }

    public ESTermQuery value(Object value) {
        this.value = value;
        return this;
    }

    public ESTermQuery fieldClassEnum(FieldClassEnum fieldClassEnum) {
        this.fieldClassEnum = fieldClassEnum;
        return this;
    }

    public ESTermQuery caseInsensitive(Boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
        return this;
    }

    public ESTermQuery boost(Float boost) {
        this.boost = boost;
        return this;
    }

    public ESTermQuery queryName(String queryName) {
        this.queryName = queryName;
        return this;
    }

    public ESTermQuery build() {
        ESTermQuery tQuery = new ESTermQuery();
        tQuery.field = field;
        tQuery.value = value;
        tQuery.fieldClassEnum = fieldClassEnum;
        tQuery.caseInsensitive = caseInsensitive;
        tQuery.boost = boost;
        tQuery.queryName = queryName;
        return tQuery;
    }

    public TermQuery buildQuery() {
        return TermQuery.of(t ->
                t.field(field)
                        .value(buildFieldValue())
                        .caseInsensitive(caseInsensitive)
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
                fieldValue = FieldValue.of((String) value);
                break;
            case LONG_CLASS:
                fieldValue = FieldValue.of((Long) value);
                break;
            case DOUBLE_CLASS:
                fieldValue = FieldValue.of((Double) value);
                break;
            case BOOLEAN_CLASS:
                fieldValue = FieldValue.of((Boolean) value);
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

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public FieldClassEnum getFieldClassEnum() {
        return fieldClassEnum;
    }

    public void setFieldClassEnum(FieldClassEnum fieldClassEnum) {
        this.fieldClassEnum = fieldClassEnum;
    }

    public Boolean getCaseInsensitive() {
        return caseInsensitive;
    }

    public void setCaseInsensitive(Boolean caseInsensitive) {
        this.caseInsensitive = caseInsensitive;
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
        return "ESTermQuery{" +
                "field='" + field + '\'' +
                ", value=" + value +
                ", fieldClassEnum=" + fieldClassEnum +
                ", caseInsensitive=" + caseInsensitive +
                ", boost=" + boost +
                ", queryName='" + queryName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ESTermQuery)) {
            return false;
        }
        ESTermQuery that = (ESTermQuery) o;
        return Objects.equals(getField(), that.getField()) &&
                Objects.equals(getValue(), that.getValue()) &&
                getFieldClassEnum() == that.getFieldClassEnum() &&
                Objects.equals(getCaseInsensitive(), that.getCaseInsensitive() &&
                        Objects.equals(getBoost(), that.getBoost()) &&
                        Objects.equals(getQueryName(), that.getQueryName()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getField(), getValue(), getFieldClassEnum(), getCaseInsensitive(), getBoost(), getQueryName());
    }
}
