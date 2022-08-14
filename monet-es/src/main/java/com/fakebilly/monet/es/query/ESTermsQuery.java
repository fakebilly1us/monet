package com.fakebilly.monet.es.query;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch._types.FieldValue;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.TermsQueryField;
import com.fakebilly.monet.es.enums.FieldClassEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ESTermsQuery
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class ESTermsQuery extends ESQuery {

    private String field;

    private List<Object> values;

    private FieldClassEnum fieldClassEnum;

    private Float boost;

    private String queryName;

    public static ESTermsQuery create() {
        return new ESTermsQuery();
    }

    public ESTermsQuery field(String field) {
        this.field = field;
        return this;
    }

    public ESTermsQuery value(List<Object> values) {
        this.values = values;
        return this;
    }

    public ESTermsQuery fieldClassEnum(FieldClassEnum fieldClassEnum) {
        this.fieldClassEnum = fieldClassEnum;
        return this;
    }

    public ESTermsQuery boost(Float boost) {
        this.boost = boost;
        return this;
    }

    public ESTermsQuery queryName(String queryName) {
        this.queryName = queryName;
        return this;
    }

    public ESTermsQuery build() {
        ESTermsQuery tsQuery = new ESTermsQuery();
        tsQuery.field = field;
        tsQuery.values = values;
        tsQuery.fieldClassEnum = fieldClassEnum;
        tsQuery.boost = boost;
        tsQuery.queryName = queryName;
        return tsQuery;
    }

    public TermsQuery buildQuery() {
        if (StrUtil.isBlank(field) || CollectionUtil.isEmpty(values)) {
            return null;
        }
        TermsQueryField termsQueryField = TermsQueryField.of(f ->
                f.value(buildFieldValue())
        );
        return TermsQuery.of(q ->
                q.field(field)
                        .terms(termsQueryField)
                        .boost(boost)
                        .queryName(queryName)
        );
    }

    private List<FieldValue> buildFieldValue() {
        if (null == fieldClassEnum) {
            return null;
        }
        if (CollectionUtil.isEmpty(values)) {
            return null;
        }
        List<FieldValue> fieldValueList = new ArrayList<>();
        FieldValue fieldValue = null;
        for (Object v : values) {
            switch (fieldClassEnum) {
                case STRING_CLASS:
                    fieldValue = FieldValue.of((String) v);
                    break;
                case LONG_CLASS:
                    fieldValue = FieldValue.of((Long) v);
                    break;
                case DOUBLE_CLASS:
                    fieldValue = FieldValue.of((Double) v);
                    break;
                case BOOLEAN_CLASS:
                    fieldValue = FieldValue.of((Boolean) v);
                    break;
                case NULL_VALUE:
                default:
                    fieldValue = FieldValue.NULL;
                    break;
            }
            fieldValueList.add(fieldValue);
        }
        return fieldValueList;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }

    public FieldClassEnum getFieldClassEnum() {
        return fieldClassEnum;
    }

    public void setFieldClassEnum(FieldClassEnum fieldClassEnum) {
        this.fieldClassEnum = fieldClassEnum;
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
        return "ESTermsQuery{" +
                "field='" + field + '\'' +
                ", values=" + values +
                ", fieldClassEnum=" + fieldClassEnum +
                ", boost=" + boost +
                ", queryName='" + queryName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ESTermsQuery)) {
            return false;
        }
        ESTermsQuery that = (ESTermsQuery) o;
        return Objects.equals(getField(), that.getField()) &&
                Objects.equals(getValues(), that.getValues()) &&
                getFieldClassEnum() == that.getFieldClassEnum() &&
                Objects.equals(getBoost(), that.getBoost()) &&
                Objects.equals(getQueryName(), that.getQueryName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getField(), getValues(), getFieldClassEnum(), getBoost(), getQueryName());
    }
}
