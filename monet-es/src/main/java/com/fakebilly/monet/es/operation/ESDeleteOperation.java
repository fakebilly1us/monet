package com.fakebilly.monet.es.operation;

import co.elastic.clients.elasticsearch.core.bulk.DeleteOperation;

import java.util.Objects;

/**
 * ESDeleteOperation
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class ESDeleteOperation extends ESBulkOperation {

    private String index;

    private String id;

    private String routing;

    private Long version;

    public static ESDeleteOperation create() {
        return new ESDeleteOperation();
    }

    public ESDeleteOperation index(String index) {
        this.index = index;
        return this;
    }

    public ESDeleteOperation id(String id) {
        this.id = id;
        return this;
    }

    public ESDeleteOperation routing(String routing) {
        this.routing = routing;
        return this;
    }

    public ESDeleteOperation version(Long version) {
        this.version = version;
        return this;
    }

    public ESDeleteOperation build() {
        ESDeleteOperation delete = new ESDeleteOperation();
        delete.index = index;
        delete.id = id;
        delete.routing = routing;
        delete.version = version;
        return delete;
    }

    public DeleteOperation buildOperation() {
        return DeleteOperation.of(co ->
                co.index(index)
                        .id(id)
                        .routing(routing)
                        .version(version)
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

    @Override
    public String toString() {
        return "ESDeleteOperation{" +
                "index='" + index + '\'' +
                ", id='" + id + '\'' +
                ", routing='" + routing + '\'' +
                ", version=" + version +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ESDeleteOperation)) {
            return false;
        }
        ESDeleteOperation that = (ESDeleteOperation) o;
        return Objects.equals(getIndex(), that.getIndex()) && Objects.equals(getId(), that.getId()) && Objects.equals(getRouting(), that.getRouting()) && Objects.equals(getVersion(), that.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndex(), getId(), getRouting(), getVersion());
    }
}
