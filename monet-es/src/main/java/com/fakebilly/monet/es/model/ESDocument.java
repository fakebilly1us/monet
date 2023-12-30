package com.fakebilly.monet.es.model;

import java.util.Objects;

/**
 * ESDocument
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class ESDocument<T> {

    /**
     * data
     */
    private T data;
    /**
     * id
     */
    private String id;
    /**
     * index
     */
    private String index;
    /**
     * score
     */
    private Double score;
    /**
     * version
     */
    private Long version;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "ESDocument{" +
                "data=" + data +
                ", id='" + id + '\'' +
                ", index='" + index + '\'' +
                ", score=" + score +
                ", version=" + version +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ESDocument)) {
            return false;
        }
        ESDocument<?> that = (ESDocument<?>) o;
        return Objects.equals(getData(), that.getData()) &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getIndex(), that.getIndex()) &&
                Objects.equals(getScore(), that.getScore()) &&
                Objects.equals(getVersion(), that.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getData(), getId(), getIndex(), getScore(), getVersion());
    }
}
