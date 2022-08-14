package com.fakebilly.monet.es.request;

import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch._types.WaitForActiveShards;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import com.fakebilly.monet.es.enums.ESWaitForActiveShardOptionsEnum;

import java.util.Objects;

/**
 * ESUpdateRequest
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class ESUpdateRequest<T, TP> extends ESRequest {

    private String index;

    private String id;

    private TP doc;

    private T upsert;

    private String routing;

    private Boolean refresh = Boolean.TRUE;

    private String type;

    private Long ifSeqNo;

    private Long ifPrimaryTerm;

    private Boolean detectNoop;

    private Boolean docAsUpsert;

    private String lang;

    private Boolean requireAlias;

    private Integer retryOnConflict;

    private Boolean scriptedUpsert;

    private Integer waitForActiveShards;

    private ESWaitForActiveShardOptionsEnum waitForActiveShardOptions;

    private String time;

    private Integer offset;

    public static <T, TP> ESUpdateRequest<T, TP> create() {
        return new ESUpdateRequest<>();
    }

    public static <T, TP> ESUpdateRequest<T, TP> create(TP doc) {
        ESUpdateRequest<T, TP> update = new ESUpdateRequest<>();
        update.doc = doc;
        return update;
    }

    public static <T, TP> ESUpdateRequest<T, TP> create(TP doc, T upsert) {
        ESUpdateRequest<T, TP> update = new ESUpdateRequest<>();
        update.doc = doc;
        update.upsert = upsert;
        return update;
    }

    public ESUpdateRequest<T, TP> index(String index) {
        this.index = index;
        return this;
    }

    public ESUpdateRequest<T, TP> id(String id) {
        this.id = id;
        return this;
    }

    public ESUpdateRequest<T, TP> doc(TP doc) {
        this.doc = doc;
        return this;
    }

    public ESUpdateRequest<T, TP> upsert(T upsert) {
        this.upsert = upsert;
        return this;
    }

    public ESUpdateRequest<T, TP> routing(String routing) {
        this.routing = routing;
        return this;
    }

    public ESUpdateRequest<T, TP> refresh(Boolean refresh) {
        this.refresh = refresh;
        return this;
    }

    public ESUpdateRequest<T, TP> type(String type) {
        this.type = type;
        return this;
    }

    public ESUpdateRequest<T, TP> ifSeqNo(Long ifSeqNo) {
        this.ifSeqNo = ifSeqNo;
        return this;
    }

    public ESUpdateRequest<T, TP> ifPrimaryTerm(Long ifPrimaryTerm) {
        this.ifPrimaryTerm = ifPrimaryTerm;
        return this;
    }

    public ESUpdateRequest<T, TP> scriptedUpsert(Boolean scriptedUpsert) {
        this.scriptedUpsert = scriptedUpsert;
        return this;
    }

    public ESUpdateRequest<T, TP> detectNoop(Boolean detectNoop) {
        this.detectNoop = detectNoop;
        return this;
    }

    public ESUpdateRequest<T, TP> docAsUpsert(Boolean docAsUpsert) {
        this.docAsUpsert = docAsUpsert;
        return this;
    }

    public ESUpdateRequest<T, TP> lang(String lang) {
        this.lang = lang;
        return this;
    }

    public ESUpdateRequest<T, TP> requireAlias(Boolean requireAlias) {
        this.requireAlias = requireAlias;
        return this;
    }

    public ESUpdateRequest<T, TP> retryOnConflict(Integer retryOnConflict) {
        this.retryOnConflict = retryOnConflict;
        return this;
    }

    public ESUpdateRequest<T, TP> waitForActiveShards(Integer waitForActiveShards) {
        this.waitForActiveShards = waitForActiveShards;
        return this;
    }

    public ESUpdateRequest<T, TP> waitForActiveShardOptions(ESWaitForActiveShardOptionsEnum waitForActiveShardOptions) {
        this.waitForActiveShardOptions = waitForActiveShardOptions;
        return this;
    }

    public ESUpdateRequest<T, TP> time(String time) {
        this.time = time;
        return this;
    }

    public ESUpdateRequest<T, TP> offset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public ESUpdateRequest<T, TP> build() {
        ESUpdateRequest<T, TP> update = new ESUpdateRequest<>();
        update.index = this.index;
        update.id = id;
        update.doc = doc;
        update.upsert = upsert;
        update.routing = routing;
        update.refresh = refresh;
        update.type = type;
        update.ifSeqNo = ifSeqNo;
        update.ifPrimaryTerm = ifPrimaryTerm;
        update.detectNoop = detectNoop;
        update.docAsUpsert = docAsUpsert;
        update.lang = lang;
        update.requireAlias = requireAlias;
        update.retryOnConflict = retryOnConflict;
        update.scriptedUpsert = scriptedUpsert;
        update.waitForActiveShards = waitForActiveShards;
        update.waitForActiveShardOptions = waitForActiveShardOptions;
        update.time = time;
        update.offset = offset;
        return update;
    }

    public UpdateRequest<T, TP> toRequest() {
        return UpdateRequest.of(co ->
                {
                    co.index(index)
                            .id(id)
                            .doc(doc)
                            .upsert(upsert)
                            .routing(routing)
                            .refresh(null == refresh ? null : (!refresh ? Refresh.False : Refresh.True))
                            .type(type)
                            .ifSeqNo(ifSeqNo)
                            .ifPrimaryTerm(ifPrimaryTerm)
                            .detectNoop(detectNoop)
                            .docAsUpsert(docAsUpsert)
                            .lang(lang)
                            .requireAlias(requireAlias)
                            .retryOnConflict(retryOnConflict)
                            .scriptedUpsert(scriptedUpsert);
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

    public TP getDoc() {
        return doc;
    }

    public void setDoc(TP doc) {
        this.doc = doc;
    }

    public T getUpsert() {
        return upsert;
    }

    public void setUpsert(T upsert) {
        this.upsert = upsert;
    }

    public String getRouting() {
        return routing;
    }

    public void setRouting(String routing) {
        this.routing = routing;
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

    public Boolean getDetectNoop() {
        return detectNoop;
    }

    public void setDetectNoop(Boolean detectNoop) {
        this.detectNoop = detectNoop;
    }

    public Boolean getDocAsUpsert() {
        return docAsUpsert;
    }

    public void setDocAsUpsert(Boolean docAsUpsert) {
        this.docAsUpsert = docAsUpsert;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Boolean getRequireAlias() {
        return requireAlias;
    }

    public void setRequireAlias(Boolean requireAlias) {
        this.requireAlias = requireAlias;
    }

    public Integer getRetryOnConflict() {
        return retryOnConflict;
    }

    public void setRetryOnConflict(Integer retryOnConflict) {
        this.retryOnConflict = retryOnConflict;
    }

    public Boolean getScriptedUpsert() {
        return scriptedUpsert;
    }

    public void setScriptedUpsert(Boolean scriptedUpsert) {
        this.scriptedUpsert = scriptedUpsert;
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
        return "ESUpdateRequest{" +
                "index='" + index + '\'' +
                ", id='" + id + '\'' +
                ", doc=" + doc +
                ", upsert=" + upsert +
                ", routing='" + routing + '\'' +
                ", refresh=" + refresh +
                ", type='" + type + '\'' +
                ", ifSeqNo=" + ifSeqNo +
                ", ifPrimaryTerm=" + ifPrimaryTerm +
                ", detectNoop=" + detectNoop +
                ", docAsUpsert=" + docAsUpsert +
                ", lang='" + lang + '\'' +
                ", requireAlias=" + requireAlias +
                ", retryOnConflict=" + retryOnConflict +
                ", scriptedUpsert=" + scriptedUpsert +
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
        if (!(o instanceof ESUpdateRequest)) {
            return false;
        }
        ESUpdateRequest<?, ?> that = (ESUpdateRequest<?, ?>) o;
        return Objects.equals(getIndex(), that.getIndex()) &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getDoc(), that.getDoc()) &&
                Objects.equals(getUpsert(), that.getUpsert()) &&
                Objects.equals(getRouting(), that.getRouting()) &&
                Objects.equals(getRefresh(), that.getRefresh()) &&
                Objects.equals(getType(), that.getType()) &&
                Objects.equals(getIfSeqNo(), that.getIfSeqNo()) &&
                Objects.equals(getIfPrimaryTerm(), that.getIfPrimaryTerm()) &&
                Objects.equals(getDetectNoop(), that.getDetectNoop()) &&
                Objects.equals(getDocAsUpsert(), that.getDocAsUpsert()) &&
                Objects.equals(getLang(), that.getLang()) &&
                Objects.equals(getRequireAlias(), that.getRequireAlias()) &&
                Objects.equals(getRetryOnConflict(), that.getRetryOnConflict()) &&
                Objects.equals(getScriptedUpsert(), that.getScriptedUpsert()) &&
                Objects.equals(getWaitForActiveShards(), that.getWaitForActiveShards()) &&
                getWaitForActiveShardOptions() == that.getWaitForActiveShardOptions() &&
                Objects.equals(getTime(), that.getTime()) &&
                Objects.equals(getOffset(), that.getOffset());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIndex(), getId(), getDoc(), getUpsert(), getRouting(), getRefresh(), getType(), getIfSeqNo(), getIfPrimaryTerm(), getDetectNoop(), getDocAsUpsert(), getLang(), getRequireAlias(), getRetryOnConflict(), getScriptedUpsert(), getWaitForActiveShards(), getWaitForActiveShardOptions(), getTime(), getOffset());
    }
}
