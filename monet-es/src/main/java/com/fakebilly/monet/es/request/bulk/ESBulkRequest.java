package com.fakebilly.monet.es.request.bulk;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.WaitForActiveShards;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import com.fakebilly.monet.es.enums.ESWaitForActiveShardOptionsEnum;
import com.fakebilly.monet.es.operation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ESBulkRequest
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class ESBulkRequest {

    private String index;

    private String pipeline;

    private Boolean refresh = Boolean.FALSE;

    private Boolean requireAlias;

    private String routing;

    private String type;

    private Integer waitForActiveShards;

    private ESWaitForActiveShardOptionsEnum waitForActiveShardOptions;

    private String time;

    private Integer offset;

    private List<ESBulkOperation> operations;

    public static ESBulkRequest create() {
        return new ESBulkRequest();
    }

    protected ESBulkRequest() {
    }

    public ESBulkRequest index(String index) {
        this.index = index;
        return this;
    }

    public ESBulkRequest pipeline(String pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    public ESBulkRequest refresh(Boolean refresh) {
        this.refresh = refresh;
        return this;
    }

    public ESBulkRequest requireAlias(Boolean requireAlias) {
        this.requireAlias = requireAlias;
        return this;
    }

    public ESBulkRequest routing(String routing) {
        this.routing = routing;
        return this;
    }

    public ESBulkRequest type(String type) {
        this.type = type;
        return this;
    }

    public ESBulkRequest waitForActiveShards(Integer waitForActiveShards) {
        this.waitForActiveShards = waitForActiveShards;
        return this;
    }

    public ESBulkRequest waitForActiveShardOptions(ESWaitForActiveShardOptionsEnum waitForActiveShardOptions) {
        this.waitForActiveShardOptions = waitForActiveShardOptions;
        return this;
    }

    public ESBulkRequest operations(List<ESBulkOperation> operations) {
        this.operations = operations;
        return this;
    }

    public ESBulkRequest time(String time) {
        this.time = time;
        return this;
    }

    public ESBulkRequest offset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public BulkRequest toRequest() {
        if (CollectionUtil.isEmpty(operations)) {
            return null;
        }
        List<BulkOperation> operationList = new ArrayList<>(operations.size());
        BulkOperation bulkOperation = null;
        for (ESBulkOperation operation : operations) {
            if (operation instanceof ESCreateOperation) {
                bulkOperation = BulkOperation.of(bo ->
                        bo.create(((ESCreateOperation<?>) operation).buildOperation())
                );
            } else if (operation instanceof ESIndexOperation) {
                bulkOperation = BulkOperation.of(bo ->
                        bo.index(((ESIndexOperation<?>) operation).buildOperation())
                );
            } else if (operation instanceof ESUpdateOperation) {
                bulkOperation = BulkOperation.of(bo ->
                        bo.update(((ESUpdateOperation<?>) operation).buildOperation())
                );
            } else if (operation instanceof ESDeleteOperation) {
                bulkOperation = BulkOperation.of(bo ->
                        bo.delete(((ESDeleteOperation) operation).buildOperation())
                );
            } else {
                bulkOperation = null;
            }
            if (null != bulkOperation) {
                operationList.add(bulkOperation);
            }
        }
        return BulkRequest.of(b ->
                {
                    b.index(index)
                            .refresh(null == refresh || !refresh ? Refresh.False : Refresh.True)
                            .routing(routing)
                            .requireAlias(requireAlias)
                            .operations(operationList)
                            .type(type);
                    Time timeOut = null;
                    if (null != offset) {
                        timeOut = Time.of(t -> t.offset(offset));
                    }
                    if (StrUtil.isNotBlank(time)) {
                        timeOut = Time.of(t -> t.time(time));
                    }
                    b.timeout(timeOut);
                    WaitForActiveShards wait = null;
                    if (null != waitForActiveShards) {
                        wait = WaitForActiveShards.of(wfas -> wfas.count(waitForActiveShards));
                    } else {
                        if (null != waitForActiveShardOptions && ESWaitForActiveShardOptionsEnum.getEnum(waitForActiveShardOptions) != null) {
                            wait = WaitForActiveShards.of(wfas -> wfas.option(ESWaitForActiveShardOptionsEnum.toWaitForActiveShardOptions(waitForActiveShardOptions)));
                        }
                    }
                    b.waitForActiveShards(wait);
                    return b;
                }
        );
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
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

    public Boolean getRequireAlias() {
        return requireAlias;
    }

    public void setRequireAlias(Boolean requireAlias) {
        this.requireAlias = requireAlias;
    }

    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
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

    public List<ESBulkOperation> getOperations() {
        return operations;
    }

    public void setOperations(List<ESBulkOperation> operations) {
        this.operations = operations;
    }

    @Override
    public String toString() {
        return "ESBulkRequest{" +
                "index='" + index + '\'' +
                ", pipeline='" + pipeline + '\'' +
                ", refresh=" + refresh +
                ", requireAlias=" + requireAlias +
                ", routing='" + routing + '\'' +
                ", type='" + type + '\'' +
                ", waitForActiveShards=" + waitForActiveShards +
                ", waitForActiveShardOptions=" + waitForActiveShardOptions +
                ", time='" + time + '\'' +
                ", offset=" + offset +
                ", operations=" + operations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ESBulkRequest)) {
            return false;
        }
        ESBulkRequest that = (ESBulkRequest) o;
        return Objects.equals(getIndex(), that.getIndex()) && Objects.equals(getPipeline(), that.getPipeline()) && Objects.equals(getRefresh(), that.getRefresh()) && Objects.equals(getRequireAlias(), that.getRequireAlias()) && Objects.equals(getRouting(), that.getRouting()) && Objects.equals(getType(), that.getType()) && Objects.equals(getWaitForActiveShards(), that.getWaitForActiveShards()) && getWaitForActiveShardOptions() == that.getWaitForActiveShardOptions() && Objects.equals(getTime(), that.getTime()) && Objects.equals(getOffset(), that.getOffset()) && Objects.equals(getOperations(), that.getOperations());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndex(), getPipeline(), getRefresh(), getRequireAlias(), getRouting(), getType(), getWaitForActiveShards(), getWaitForActiveShardOptions(), getTime(), getOffset(), getOperations());
    }
}
