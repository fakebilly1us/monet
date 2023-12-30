package com.fakebilly.monet.es.request;

import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.WaitForActiveShards;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import com.fakebilly.monet.es.enums.ESWaitForActiveShardOptionsEnum;

import java.util.Objects;

/**
 * ESDeleteRequest
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class ESDeleteRequest extends ESRequest {

    private String index;

    private String id;

    private String routing;

    private Long version;

    private Boolean refresh = Boolean.TRUE;

    private String type;

    private Integer waitForActiveShards;

    private ESWaitForActiveShardOptionsEnum waitForActiveShardOptions;

    private String time;

    private Integer offset;

    public static ESDeleteRequest create() {
        return new ESDeleteRequest();
    }

    public ESDeleteRequest index(String index) {
        this.index = index;
        return this;
    }

    public ESDeleteRequest id(String id) {
        this.id = id;
        return this;
    }

    public ESDeleteRequest routing(String routing) {
        this.routing = routing;
        return this;
    }

    public ESDeleteRequest version(Long version) {
        this.version = version;
        return this;
    }

    public ESDeleteRequest refresh(Boolean refresh) {
        this.refresh = refresh;
        return this;
    }

    public ESDeleteRequest type(String type) {
        this.type = type;
        return this;
    }

    public ESDeleteRequest waitForActiveShards(Integer waitForActiveShards) {
        this.waitForActiveShards = waitForActiveShards;
        return this;
    }

    public ESDeleteRequest waitForActiveShardOptions(ESWaitForActiveShardOptionsEnum waitForActiveShardOptions) {
        this.waitForActiveShardOptions = waitForActiveShardOptions;
        return this;
    }

    public ESDeleteRequest time(String time) {
        this.time = time;
        return this;
    }

    public ESDeleteRequest offset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public ESDeleteRequest build() {
        ESDeleteRequest create = new ESDeleteRequest();
        create.index = index;
        create.id = id;
        create.routing = routing;
        create.version = version;
        create.refresh = refresh;
        create.type = type;
        create.waitForActiveShards = waitForActiveShards;
        create.waitForActiveShardOptions = waitForActiveShardOptions;
        create.time = time;
        create.offset = offset;
        return create;
    }

    public DeleteRequest toRequest() {
        return DeleteRequest.of(co ->
                {
                    co.index(index)
                            .id(id)
                            .routing(routing)
                            .version(version)
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
        return "ESDeleteRequest{" +
                "index='" + index + '\'' +
                ", id='" + id + '\'' +
                ", routing='" + routing + '\'' +
                ", version=" + version +
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
        if (!(o instanceof ESDeleteRequest)) {
            return false;
        }
        ESDeleteRequest that = (ESDeleteRequest) o;
        return Objects.equals(getIndex(), that.getIndex()) &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getRouting(), that.getRouting()) &&
                Objects.equals(getVersion(), that.getVersion()) &&
                Objects.equals(getRefresh(), that.getRefresh()) &&
                Objects.equals(getType(), that.getType()) &&
                Objects.equals(getWaitForActiveShards(), that.getWaitForActiveShards()) &&
                getWaitForActiveShardOptions() == that.getWaitForActiveShardOptions() &&
                Objects.equals(getTime(), that.getTime()) &&
                Objects.equals(getOffset(), that.getOffset());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndex(), getId(), getRouting(), getVersion(), getRefresh(), getType(), getWaitForActiveShards(), getWaitForActiveShardOptions(), getTime(), getOffset());
    }
}
