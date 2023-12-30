package com.fakebilly.monet.es.request;

import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.WaitForActiveShards;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import com.fakebilly.monet.es.enums.ESOpTypeEnum;
import com.fakebilly.monet.es.enums.ESWaitForActiveShardOptionsEnum;

import java.util.Objects;

/**
 * ESIndexRequest
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class ESIndexRequest<T> extends ESRequest {

    private String index;

    private String id;

    private T document;

    private String routing;

    private Long version;

    private String pipeline;

    private Boolean refresh = Boolean.TRUE;

    private String type;

    private ESOpTypeEnum opType = ESOpTypeEnum.INDEX;

    private Long ifSeqNo;

    private Long ifPrimaryTerm;

    private Integer waitForActiveShards;

    private ESWaitForActiveShardOptionsEnum waitForActiveShardOptions;

    private String time;

    private Integer offset;

    public static <T> ESIndexRequest<T> create() {
        return new ESIndexRequest<>();
    }

    public static <T> ESIndexRequest<T> create(T document) {
        ESIndexRequest<T> index = new ESIndexRequest<>();
        index.document = document;
        return index;
    }

    public ESIndexRequest<T> index(String index) {
        this.index = index;
        return this;
    }

    public ESIndexRequest<T> id(String id) {
        this.id = id;
        return this;
    }

    public ESIndexRequest<T> document(T document) {
        this.document = document;
        return this;
    }

    public ESIndexRequest<T> routing(String routing) {
        this.routing = routing;
        return this;
    }

    public ESIndexRequest<T> version(Long version) {
        this.version = version;
        return this;
    }

    public ESIndexRequest<T> pipeline(String pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    public ESIndexRequest<T> refresh(Boolean refresh) {
        this.refresh = refresh;
        return this;
    }

    public ESIndexRequest<T> type(String type) {
        this.type = type;
        return this;
    }

    public ESIndexRequest<T> opType(ESOpTypeEnum opType) {
        this.opType = opType;
        return this;
    }

    public ESIndexRequest<T> ifSeqNo(Long ifSeqNo) {
        this.ifSeqNo = ifSeqNo;
        return this;
    }

    public ESIndexRequest<T> ifPrimaryTerm(Long ifPrimaryTerm) {
        this.ifPrimaryTerm = ifPrimaryTerm;
        return this;
    }

    public ESIndexRequest<T> waitForActiveShards(Integer waitForActiveShards) {
        this.waitForActiveShards = waitForActiveShards;
        return this;
    }

    public ESIndexRequest<T> waitForActiveShardOptions(ESWaitForActiveShardOptionsEnum waitForActiveShardOptions) {
        this.waitForActiveShardOptions = waitForActiveShardOptions;
        return this;
    }

    public ESIndexRequest<T> time(String time) {
        this.time = time;
        return this;
    }

    public ESIndexRequest<T> offset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public ESIndexRequest<T> build() {
        ESIndexRequest<T> index = new ESIndexRequest<>();
        index.index = this.index;
        index.id = id;
        index.document = document;
        index.routing = routing;
        index.version = version;
        index.pipeline = pipeline;
        index.refresh = refresh;
        index.type = type;
        index.opType = opType;
        index.ifSeqNo = ifSeqNo;
        index.ifPrimaryTerm = ifPrimaryTerm;
        index.waitForActiveShards = waitForActiveShards;
        index.waitForActiveShardOptions = waitForActiveShardOptions;
        index.time = time;
        index.offset = offset;
        return index;
    }

    public IndexRequest<T> toRequest() {
        return IndexRequest.of(co ->
                {
                    co.index(index)
                            .id(id)
                            .document(document)
                            .routing(routing)
                            .version(version)
                            .pipeline(pipeline)
                            .refresh(null == refresh ? null : (!refresh ? Refresh.False : Refresh.True))
                            .type(type)
                            .opType(null == opType ? null : ESOpTypeEnum.toOpType(opType))
                            .ifSeqNo(ifSeqNo)
                            .ifPrimaryTerm(ifPrimaryTerm);
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

    public ESOpTypeEnum getOpType() {
        return opType;
    }

    public void setOpType(ESOpTypeEnum opType) {
        this.opType = opType;
    }

    public Long getIfSeqNo() {
        return ifSeqNo;
    }

    public void setIfSeqNo(Long ifSeqNo) {
        this.ifSeqNo = ifSeqNo;
    }

    public Long getIfPrimaryTerm() {
        return ifPrimaryTerm;
    }

    public void setIfPrimaryTerm(Long ifPrimaryTerm) {
        this.ifPrimaryTerm = ifPrimaryTerm;
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
        return "ESIndexRequest{" +
                "index='" + index + '\'' +
                ", id='" + id + '\'' +
                ", document=" + document +
                ", routing='" + routing + '\'' +
                ", version=" + version +
                ", pipeline='" + pipeline + '\'' +
                ", refresh=" + refresh +
                ", type='" + type + '\'' +
                ", opType=" + opType +
                ", ifSeqNo=" + ifSeqNo +
                ", ifPrimaryTerm=" + ifPrimaryTerm +
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
        if (!(o instanceof ESIndexRequest)) {
            return false;
        }
        ESIndexRequest<?> that = (ESIndexRequest<?>) o;
        return Objects.equals(getIndex(), that.getIndex()) &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getDocument(), that.getDocument()) &&
                Objects.equals(getRouting(), that.getRouting()) &&
                Objects.equals(getVersion(), that.getVersion()) &&
                Objects.equals(getPipeline(), that.getPipeline()) &&
                Objects.equals(getRefresh(), that.getRefresh()) &&
                Objects.equals(getType(), that.getType()) &&
                getOpType() == that.getOpType() &&
                Objects.equals(getIfSeqNo(), that.getIfSeqNo()) &&
                Objects.equals(getIfPrimaryTerm(), that.getIfPrimaryTerm()) &&
                Objects.equals(getWaitForActiveShards(), that.getWaitForActiveShards()) &&
                getWaitForActiveShardOptions() == that.getWaitForActiveShardOptions() &&
                Objects.equals(getTime(), that.getTime()) &&
                Objects.equals(getOffset(), that.getOffset());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndex(), getId(), getDocument(), getRouting(), getVersion(), getPipeline(), getRefresh(), getType(), getOpType(), getIfSeqNo(), getIfPrimaryTerm(), getWaitForActiveShards(), getWaitForActiveShardOptions(), getTime(), getOffset());
    }
}
