package com.fakebilly.monet.mq.config;

import java.util.Objects;

/**
 * MQConfigInfo
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class MQConfigInfo {

    private String namesrvAddr;

    private Integer reconsumerTimes;

    private Integer maxReconsumeTimes;

    private Integer sendMsgTimeout;

    private Integer retryTimesWhenSendFailed;

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public Integer getReconsumerTimes() {
        return reconsumerTimes;
    }

    public void setReconsumerTimes(Integer reconsumerTimes) {
        this.reconsumerTimes = reconsumerTimes;
    }

    public Integer getMaxReconsumeTimes() {
        return maxReconsumeTimes;
    }

    public void setMaxReconsumeTimes(Integer maxReconsumeTimes) {
        this.maxReconsumeTimes = maxReconsumeTimes;
    }

    public Integer getSendMsgTimeout() {
        return sendMsgTimeout;
    }

    public void setSendMsgTimeout(Integer sendMsgTimeout) {
        this.sendMsgTimeout = sendMsgTimeout;
    }

    public Integer getRetryTimesWhenSendFailed() {
        return retryTimesWhenSendFailed;
    }

    public void setRetryTimesWhenSendFailed(Integer retryTimesWhenSendFailed) {
        this.retryTimesWhenSendFailed = retryTimesWhenSendFailed;
    }

    @Override
    public String toString() {
        return "MQConfigInfo{" +
                "namesrvAddr='" + namesrvAddr + '\'' +
                ", reconsumerTimes=" + reconsumerTimes +
                ", maxReconsumeTimes=" + maxReconsumeTimes +
                ", sendMsgTimeout=" + sendMsgTimeout +
                ", retryTimesWhenSendFailed=" + retryTimesWhenSendFailed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MQConfigInfo)) {
            return false;
        }
        MQConfigInfo that = (MQConfigInfo) o;
        return Objects.equals(getNamesrvAddr(), that.getNamesrvAddr()) &&
                Objects.equals(getReconsumerTimes(), that.getReconsumerTimes()) &&
                Objects.equals(getMaxReconsumeTimes(), that.getMaxReconsumeTimes()) &&
                Objects.equals(getSendMsgTimeout(), that.getSendMsgTimeout());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNamesrvAddr(), getReconsumerTimes(), getMaxReconsumeTimes(), getSendMsgTimeout());
    }
}
