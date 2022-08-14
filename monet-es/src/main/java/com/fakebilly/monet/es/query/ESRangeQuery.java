package com.fakebilly.monet.es.query;

import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.json.JsonData;
import com.fakebilly.monet.es.enums.ESRangeRelationEnum;

import java.util.Objects;

/**
 * ESRangeQuery
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class ESRangeQuery extends ESQuery {

    private String field;

    private Object gt;

    private Object gte;

    private Object lt;

    private Object lte;

    private Object from;

    private Object to;

    private String format;

    private String timeZone;

    private ESRangeRelationEnum rangeRelation;

    public static ESRangeQuery create() {
        return new ESRangeQuery();
    }

    public ESRangeQuery field(String field) {
        this.field = field;
        return this;
    }

    public ESRangeQuery gt(Object gt) {
        this.gt = gt;
        return this;
    }

    public ESRangeQuery gte(Object gte) {
        this.gte = gte;
        return this;
    }

    public ESRangeQuery lt(Object lt) {
        this.lt = lt;
        return this;
    }

    public ESRangeQuery lte(Object lte) {
        this.lte = lte;
        return this;
    }

    public ESRangeQuery from(Object from) {
        this.from = from;
        return this;
    }

    public ESRangeQuery to(Object to) {
        this.to = to;
        return this;
    }

    public ESRangeQuery format(String format) {
        this.format = format;
        return this;
    }

    public ESRangeQuery timeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public ESRangeQuery rangeRelation(ESRangeRelationEnum rangeRelation) {
        this.rangeRelation = rangeRelation;
        return this;
    }

    public ESRangeQuery build() {
        ESRangeQuery rQuery = new ESRangeQuery();
        rQuery.field = field;
        rQuery.gt = gt;
        rQuery.gte = gte;
        rQuery.lt = lt;
        rQuery.lte = lte;
        rQuery.from = from;
        rQuery.to = to;
        rQuery.format = format;
        rQuery.timeZone = timeZone;
        rQuery.rangeRelation = rangeRelation;
        return rQuery;
    }

    public RangeQuery buildQuery() {
        return RangeQuery.of(r -> {
                    r.field(field).format(format).timeZone(timeZone).relation(ESRangeRelationEnum.toRangeRelation(rangeRelation));
                    if (null != gt) {
                        r.gt(JsonData.of(gt));
                    }
                    if (null != gte) {
                        r.gte(JsonData.of(gte));
                    }
                    if (null != lt) {
                        r.lt(JsonData.of(lt));
                    }
                    if (null != lte) {
                        r.lte(JsonData.of(lte));
                    }
                    if (null != from) {
                        r.from(JsonData.of(from));
                    }
                    if (null != to) {
                        r.to(JsonData.of(to));
                    }
                    if (null != gt) {
                        r.gt(JsonData.of(gt));
                    }
                    if (null != gt) {
                        r.gt(JsonData.of(gt));
                    }
                    return r;
                }
        );
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getGt() {
        return gt;
    }

    public void setGt(Object gt) {
        this.gt = gt;
    }

    public Object getGte() {
        return gte;
    }

    public void setGte(Object gte) {
        this.gte = gte;
    }

    public Object getLt() {
        return lt;
    }

    public void setLt(Object lt) {
        this.lt = lt;
    }

    public Object getLte() {
        return lte;
    }

    public void setLte(Object lte) {
        this.lte = lte;
    }

    public Object getFrom() {
        return from;
    }

    public void setFrom(Object from) {
        this.from = from;
    }

    public Object getTo() {
        return to;
    }

    public void setTo(Object to) {
        this.to = to;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public ESRangeRelationEnum getRangeRelation() {
        return rangeRelation;
    }

    public void setRangeRelation(ESRangeRelationEnum rangeRelation) {
        this.rangeRelation = rangeRelation;
    }

    @Override
    public String toString() {
        return "ESRangeQuery{" +
                "field='" + field + '\'' +
                ", gt=" + gt +
                ", gte=" + gte +
                ", lt=" + lt +
                ", lte=" + lte +
                ", from=" + from +
                ", to=" + to +
                ", format='" + format + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", rangeRelation=" + rangeRelation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ESRangeQuery)) {
            return false;
        }
        ESRangeQuery that = (ESRangeQuery) o;
        return Objects.equals(getField(), that.getField()) &&
                Objects.equals(getGt(), that.getGt()) &&
                Objects.equals(getGte(), that.getGte()) &&
                Objects.equals(getLt(), that.getLt()) &&
                Objects.equals(getLte(), that.getLte()) &&
                Objects.equals(getFrom(), that.getFrom()) &&
                Objects.equals(getTo(), that.getTo()) &&
                Objects.equals(getFormat(), that.getFormat()) &&
                Objects.equals(getTimeZone(), that.getTimeZone()) &&
                getRangeRelation() == that.getRangeRelation();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getField(), getGt(), getGte(), getLt(), getLte(), getFrom(), getTo(), getFormat(), getTimeZone(), getRangeRelation());
    }
}
