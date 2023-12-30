package com.fakebilly.monet.es.operation;

import co.elastic.clients.elasticsearch.core.bulk.IndexOperation;

import java.util.Objects;

/**
 * ESIndexOperation
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class ESIndexOperation<T> extends ESBulkOperation {

    private String index;

    private String id;

    private T document;

    private String routing;

    private Long version;

    private String pipeline;

    private Boolean requireAlias;

    public static <T> ESIndexOperation<T> create() {
        return new ESIndexOperation<>();
    }

    public static <T> ESIndexOperation<T> create(T document) {
        ESIndexOperation<T> index = new ESIndexOperation<>();
        index.document = document;
        return index;
    }

    public ESIndexOperation<T> index(String index) {
        this.index = index;
        return this;
    }

    public ESIndexOperation<T> id(String id) {
        this.id = id;
        return this;
    }

    public ESIndexOperation<T> document(T document) {
        this.document = document;
        return this;
    }

    public ESIndexOperation<T> routing(String routing) {
        this.routing = routing;
        return this;
    }

    public ESIndexOperation<T> version(Long version) {
        this.version = version;
        return this;
    }

    public ESIndexOperation<T> pipeline(String pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    public ESIndexOperation<T> requireAlias(Boolean requireAlias) {
        this.requireAlias = requireAlias;
        return this;
    }

    public ESIndexOperation<T> build() {
        ESIndexOperation<T> index = new ESIndexOperation<>();
        index.index = this.index;
        index.id = id;
        index.document = document;
        index.routing = routing;
        index.version = version;
        index.pipeline = pipeline;
        index.requireAlias = requireAlias;
        return index;
    }

    public IndexOperation<T> buildOperation() {
        return IndexOperation.of(co ->
                co.index(index)
                        .id(id)
                        .document(document)
                        .routing(routing)
                        .version(version)
                        .pipeline(pipeline)
                        .requireAlias(requireAlias)
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

    public String getPipeline() {
        return pipeline;
    }

    public void setPipeline(String pipeline) {
        this.pipeline = pipeline;
    }

    public Boolean getRequireAlias() {
        return requireAlias;
    }

    public void setRequireAlias(Boolean requireAlias) {
        this.requireAlias = requireAlias;
    }

    @Override
    public String toString() {
        return "ESIndexOperation{" +
                "index='" + index + '\'' +
                ", id='" + id + '\'' +
                ", document=" + document +
                ", routing='" + routing + '\'' +
                ", version=" + version +
                ", pipeline='" + pipeline + '\'' +
                ", requireAlias=" + requireAlias +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ESIndexOperation)) {
            return false;
        }
        ESIndexOperation<?> that = (ESIndexOperation<?>) o;
        return Objects.equals(getIndex(), that.getIndex()) && Objects.equals(getId(), that.getId()) && Objects.equals(getDocument(), that.getDocument()) && Objects.equals(getRouting(), that.getRouting()) && Objects.equals(getVersion(), that.getVersion()) && Objects.equals(getPipeline(), that.getPipeline()) && Objects.equals(getRequireAlias(), that.getRequireAlias());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndex(), getId(), getDocument(), getRouting(), getVersion(), getPipeline(), getRequireAlias());
    }
}
