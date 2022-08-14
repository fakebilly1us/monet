package com.fakebilly.monet.es.operation;

import co.elastic.clients.elasticsearch.core.bulk.UpdateOperation;
import com.fakebilly.monet.es.exception.ElasticSearchException;

import java.util.Map;
import java.util.Objects;

/**
 * ESUpdateOperation
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class ESUpdateOperation<T> extends ESBulkOperation {

    public static final String DOCUMENT_KEY = "doc";

    private String index;

    private String id;

    /**
     * Map -> "doc" : T
     */
    private T document;

    private String routing;

    private Long version;

    private Integer retryOnConflict;

    private Boolean requireAlias;

    public static <T> ESUpdateOperation<T> create() {
        return new ESUpdateOperation<>();
    }

    public static <T> ESUpdateOperation<T> create(T document) {
        if (!(document instanceof Map)) {
            throw new ElasticSearchException("ESUpdateOperation's document must be Map with {\"doc\" : T}");
        }
        ESUpdateOperation<T> update = new ESUpdateOperation<>();
        update.document = document;
        return update;
    }

    public ESUpdateOperation<T> index(String index) {
        this.index = index;
        return this;
    }

    public ESUpdateOperation<T> id(String id) {
        this.id = id;
        return this;
    }

    public ESUpdateOperation<T> document(T document) {
        if (!(document instanceof Map)) {
            throw new ElasticSearchException("ESUpdateOperation's document must be Map with {\"doc\" : T}");
        }
        this.document = document;
        return this;
    }

    public ESUpdateOperation<T> routing(String routing) {
        this.routing = routing;
        return this;
    }

    public ESUpdateOperation<T> version(Long version) {
        this.version = version;
        return this;
    }

    public ESUpdateOperation<T> requireAlias(Boolean requireAlias) {
        this.requireAlias = requireAlias;
        return this;
    }

    public ESUpdateOperation<T> retryOnConflict(Integer retryOnConflict) {
        this.retryOnConflict = retryOnConflict;
        return this;
    }

    public ESUpdateOperation<T> build() {
        if (!(document instanceof Map)) {
            throw new ElasticSearchException("ESUpdateOperation's document must be Map with {\"doc\" : T}");
        }
        ESUpdateOperation<T> update = new ESUpdateOperation<>();
        update.index = index;
        update.id = id;
        update.document = document;
        update.routing = routing;
        update.version = version;
        update.retryOnConflict = retryOnConflict;
        update.requireAlias = requireAlias;
        return update;
    }

    public UpdateOperation<T> buildOperation() {
        if (!(document instanceof Map)) {
            throw new ElasticSearchException("ESUpdateOperation's document must be Map with {\"doc\" : T}");
        }
        return UpdateOperation.of(uo ->
                uo.index(index)
                        .id(id)
                        .document(document)
                        .routing(routing)
                        .version(version)
                        .requireAlias(requireAlias)
                        .retryOnConflict(retryOnConflict)
        );
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public T getDocument() {
        return document;
    }

    public void setDocument(T document) {
        if (!(document instanceof Map)) {
            throw new ElasticSearchException("ESUpdateOperation's document must be Map with {\"doc\" : T}");
        }
        this.document = document;
    }

    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Integer getRetryOnConflict() {
        return retryOnConflict;
    }

    public void setRetryOnConflict(Integer retryOnConflict) {
        this.retryOnConflict = retryOnConflict;
    }

    public Boolean getRequireAlias() {
        return requireAlias;
    }

    public void setRequireAlias(Boolean requireAlias) {
        this.requireAlias = requireAlias;
    }

    @Override
    public String toString() {
        return "ESUpdateOperation{" +
                "index='" + index + '\'' +
                ", id='" + id + '\'' +
                ", document=" + document +
                ", routing='" + routing + '\'' +
                ", version=" + version +
                ", retryOnConflict=" + retryOnConflict +
                ", requireAlias=" + requireAlias +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ESUpdateOperation)) {
            return false;
        }
        ESUpdateOperation<?> that = (ESUpdateOperation<?>) o;
        return Objects.equals(getIndex(), that.getIndex()) && Objects.equals(getId(), that.getId()) && Objects.equals(getDocument(), that.getDocument()) && Objects.equals(getRouting(), that.getRouting()) && Objects.equals(getVersion(), that.getVersion()) && Objects.equals(getRetryOnConflict(), that.getRetryOnConflict()) && Objects.equals(getRequireAlias(), that.getRequireAlias());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndex(), getId(), getDocument(), getRouting(), getVersion(), getRetryOnConflict(), getRequireAlias());
    }
}
