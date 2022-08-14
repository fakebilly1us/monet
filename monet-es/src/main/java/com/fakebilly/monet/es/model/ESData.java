package com.fakebilly.monet.es.model;

import java.util.List;
import java.util.Objects;

/**
 * ESData
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class ESData<T> {

    /**
     * dataList
     */
    private List<ESDocument<T>> dataList;
    /**
     * total
     */
    private long total;
    /**
     * maxScore
     */
    private Double maxScore;

    public List<ESDocument<T>> getDataList() {
        return dataList;
    }

    public void setDataList(List<ESDocument<T>> dataList) {
        this.dataList = dataList;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Double getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(Double maxScore) {
        this.maxScore = maxScore;
    }

    @Override
    public String toString() {
        return "ESData{" +
                "dataList=" + dataList +
                ", total=" + total +
                ", maxScore=" + maxScore +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ESData)) {
            return false;
        }
        ESData<?> esData = (ESData<?>) o;
        return getTotal() == esData.getTotal() &&
                Objects.equals(getDataList(), esData.getDataList()) &&
                Objects.equals(getMaxScore(), esData.getMaxScore());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDataList(), getTotal(), getMaxScore());
    }
}
