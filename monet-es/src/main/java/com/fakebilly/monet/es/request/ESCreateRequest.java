package com.fakebilly.monet.es.request;

import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.WaitForActiveShards;
import co.elastic.clients.elasticsearch.core.CreateRequest;
import com.fakebilly.monet.es.enums.ESWaitForActiveShardOptionsEnum;

import java.util.Objects;

/**
 * ESCreateRequest
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class ESCreateRequest<T> extends ESRequest {

    private String index;

    private String id;

    private T document;

    private String routing;

    private Long version;

    private String pipeline;

    private Boolean refresh = Boolean.TRUE;

    private String type;

    private Integer waitForActiveShards;

    private ESWaitForActiveShardOptionsEnum waitForActiveShardOptions;

    private String time;

    private Integer offset;

    public static <T> ESCreateRequest<T> create() {
        return new ESCreateRequest<>();
    }

    public static <T> ESCreateRequest<T> create(T document) {
        ESCreateRequest<T> create = new ESCreateRequest<>();
        create.document = document;
        return create;
    }

    public ESCreateRequest<T> index(String index) {
        this.index = index;
        return this;
    }

    public ESCreateRequest<T> id(String id) {
        this.id = id;
        return this;
    }

    public ESCreateRequest<T> document(T document) {
        this.document = document;
        return this;
    }

    public ESCreateRequest<T> routing(String routing) {
        this.routing = routing;
        return this;
    }

    public ESCreateRequest<T> version(Long version) {
        this.version = version;
        return this;
    }

    public ESCreateRequest<T> pipeline(String pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    public ESCreateRequest<T> refresh(Boolean refresh) {
        this.refresh = refresh;
        return this;
    }

    public ESCreateRequest<T> type(String type) {
        this.type = type;
        return this;
    }

    public ESCreateRequest<T> waitForActiveShards(Integer waitForActiveShards) {
        this.waitForActiveShards = waitForActiveShards;
        return this;
    }

    public ESCreateRequest<T> waitForActiveShardOptions(ESWaitForActiveShardOptionsEnum waitForActiveShardOptions) {
        this.waitForActiveShardOptions = waitForActiveShardOptions;
        return this;
    }

    public ESCreateRequest<T> time(String time) {
        this.time = time;
        return this;
    }

    public ESCreateRequest<T> offset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public ESCreateRequest<T> build() {
        ESCreateRequest<T> create = new ESCreateRequest<>();
        create.index = index;
        create.id = id;
        create.document = document;
        create.routing = routing;
        create.version = version;
        create.pipeline = pipeline;
        create.refresh = refresh;
        create.type = type;
        create.waitForActiveShards = waitForActiveShards;
        create.waitForActiveShardOptions = waitForActiveShardOptions;
        create.time = time;
        create.offset = offset;
        return create;
    }

    public CreateRequest<T> toRequest() {
        return CreateRequest.of(co ->
                {
                    co.index(index)
                            .id(id)
                            .document(document)
                            .routing(routing)
                            .version(version)
                            .pipeline(pipeline)
                            .refresh(null == refresh ? null : (!refresh ? Refresh.False : Refresh.True))
                            .type(type);
                    Time timeOut = null;
                    if (null != offset) {
                        timeOut = Time.of(t -> t.offset(offset));
                    }
                    if (StrUtil.isNotBlank(time)) {
                        timeOut = Time.of(t -> t.time(time));
                    }
                    co.timeout(timeOut);
                    WaitForActiveShards wait = null;
                    if (null != waitForActiveShards) {
                        wait = WaitForActiveShards.of(wfas -> wfas.count(waitForActiveShards));
                    } else {
                        if (null != waitForActiveShardOptions && ESWaitForActiveShardOptionsEnum.getEnum(waitForActiveShardOptions) != null) {
                            wait = WaitForActiveShards.of(wfas -> wfas.option(ESWaitForActiveShardOptionsEnum.toWaitForActiveShardOptions(waitForActiveShardOptions)));
                        }
                    }
                    co.waitForActiveShards(wait);
                    return co;
                }
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

    public Boolean getRefresh() {
        return refresh;
    }

    public void setRefresh(Boolean refresh) {
        this.refresh = refresh;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getWaitForActiveShards() {
        return waitForActiveShards;
    }

    public void setWaitForActiveShards(Integer waitForActiveShards) {
        this.waitForActiveShards = waitForActiveShards;
    }

    public ESWaitForActiveShardOptionsEnum getWaitForActiveShardOptions() {
        return waitForActiveShardOptions;
    }

    public void setWaitForActiveShardOptions(ESWaitForActiveShardOptionsEnum waitForActiveShardOptions) {
        this.waitForActiveShardOptions = waitForActiveShardOptions;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "ESCreateRequest{" +
                "index='" + index + '\'' +
                ", id='" + id + '\'' +
                ", document=" + document +
                ", routing='" + routing + '\'' +
                ", version=" + version +
                ", pipeline='" + pipeline + '\'' +
                ", refresh=" + refresh +
                ", type='" + type + '\'' +
                ", waitForActiveShards=" + waitForActiveShards +
                ", waitForActiveShardOptions=" + waitForActiveShardOptions +
                ", time='" + time + '\'' +
                ", offset=" + offset +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ESCreateRequest)) {
            return false;
        }
        ESCreateRequest<?> that = (ESCreateRequest<?>) o;
        return Objects.equals(getIndex(), that.getIndex()) &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getDocument(), that.getDocument()) &&
                Objects.equals(getRouting(), that.getRouting()) &&
                Objects.equals(getVersion(), that.getVersion()) &&
                Objects.equals(getPipeline(), that.getPipeline()) &&
                Objects.equals(getRefresh(), that.getRefresh()) &&
                Objects.equals(getType(), that.getType()) &&
                Objects.equals(getWaitForActiveShards(), that.getWaitForActiveShards()) &&
                getWaitForActiveShardOptions() == that.getWaitForActiveShardOptions() &&
                Objects.equals(getTime(), that.getTime()) &&
                Objects.equals(getOffset(), that.getOffset());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndex(), getId(), getDocument(), getRouting(), getVersion(), getPipeline(), getRefresh(), getType(), getWaitForActiveShards(), getWaitForActiveShardOptions(), getTime(), getOffset());
    }
}
