package com.fakebilly.monet.es.operation;

import co.elastic.clients.elasticsearch.core.bulk.CreateOperation;

import java.util.Objects;

/**
 * ESCreateOperation
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class ESCreateOperation<T> extends ESBulkOperation {

    private String index;

    private String id;

    private T document;

    private String routing;

    private Long version;

    private String pipeline;

    private Boolean requireAlias;

    public static <T> ESCreateOperation<T> create() {
        return new ESCreateOperation<>();
    }

    public static <T> ESCreateOperation<T> create(T document) {
        ESCreateOperation<T> create = new ESCreateOperation<>();
        create.document = document;
        return create;
    }

    public ESCreateOperation<T> index(String index) {
        this.index = index;
        return this;
    }

    public ESCreateOperation<T> id(String id) {
        this.id = id;
        return this;
    }

    public ESCreateOperation<T> document(T document) {
        this.document = document;
        return this;
    }

    public ESCreateOperation<T> routing(String routing) {
        this.routing = routing;
        return this;
    }

    public ESCreateOperation<T> version(Long version) {
        this.version = version;
        return this;
    }

    public ESCreateOperation<T> pipeline(String pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    public ESCreateOperation<T> requireAlias(Boolean requireAlias) {
        this.requireAlias = requireAlias;
        return this;
    }

    public ESCreateOperation<T> build() {
        ESCreateOperation<T> create = new ESCreateOperation<>();
        create.index = index;
        create.id = id;
        create.document = document;
        create.routing = routing;
        create.version = version;
        create.pipeline = pipeline;
        create.requireAlias = requireAlias;
        return create;
    }

    public CreateOperation<T> buildOperation() {
        return CreateOperation.of(co ->
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
        return "ESCreateOperation{" +
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
        if (!(o instanceof ESCreateOperation)) {
            return false;
        }
        ESCreateOperation<?> that = (ESCreateOperation<?>) o;
        return Objects.equals(getIndex(), that.getIndex()) && Objects.equals(getId(), that.getId()) && Objects.equals(getDocument(), that.getDocument()) && Objects.equals(getRouting(), that.getRouting()) && Objects.equals(getVersion(), that.getVersion()) && Objects.equals(getPipeline(), that.getPipeline()) && Objects.equals(getRequireAlias(), that.getRequireAlias());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndex(), getId(), getDocument(), getRouting(), getVersion(), getPipeline(), getRequireAlias());
    }
}
