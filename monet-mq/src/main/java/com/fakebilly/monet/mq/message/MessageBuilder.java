package com.fakebilly.monet.mq.message;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.fakebilly.monet.mq.enums.SerializeTypeEnum;
import com.fakebilly.monet.mq.exception.MQException;
import org.apache.rocketmq.common.message.Message;
import org.springframework.util.SerializationUtils;

import java.io.Serializable;

/**
 * MessageBuilder
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class MessageBuilder implements Serializable {

    private static final long serialVersionUID = -7598740068419916286L;

    private String topic;
    private String tag;
    private String key;
    private MessageBodyModel message;
    private Integer delayTimeLevel;
    private SerializeTypeEnum serializeType = SerializeTypeEnum.JSON_SERIALIZE;

    public static MessageBuilder create() {
        return new MessageBuilder();
    }

    protected MessageBuilder() {
    }

    public MessageBuilder topic(String topic) {
        this.topic = topic;
        return this;
    }

    public MessageBuilder tag(String tag) {
        this.tag = tag;
        return this;
    }

    public MessageBuilder key(String key) {
        this.key = key;
        return this;
    }

    public MessageBuilder delayTimeLevel(Integer delayTimeLevel) {
        this.delayTimeLevel = delayTimeLevel;
        return this;
    }

    public MessageBuilder message(MessageBodyModel message) {
        this.message = message;
        return this;
    }

    public MessageBuilder serializeType(SerializeTypeEnum serializeType) {
        this.serializeType = serializeType;
        return this;
    }

    public Message build() {
        return build(this.serializeType);
    }

    public Message build(SerializeTypeEnum serializeType) {
        if (StrUtil.isBlank(this.topic)) {
            throw new MQException("no topic defined to send message");
        }
        if (null == message) {
            throw new MQException("the message object cannot be null");
        }
        if (StrUtil.isBlank(this.key)) {
            throw new MQException("the key cannot be empty");
        }

        if (StrUtil.isBlank(this.message.getId())) {
            this.message.setId(this.getKey());
        }
        Message msg;
        switch (serializeType) {
            case JDK_SERIALIZE:
                msg = new Message(this.topic, SerializationUtils.serialize(message));
                break;
            default:
                msg = new Message(this.topic, JSONObject.toJSONString(message).getBytes());
                break;
        }

        if (StrUtil.isNotBlank(tag)) {
            msg.setTags(tag);
        }

        if (StrUtil.isNotBlank(key)) {
            msg.setKeys(key);
        }

        if (delayTimeLevel != null && delayTimeLevel > 0) {
            if (delayTimeLevel > 18) {
                throw new MQException("delayTimeLevel must be between 1 and 18");
            } else {
                msg.setDelayTimeLevel(delayTimeLevel);
            }
        }

        return msg;

    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MessageBodyModel getMessage() {
        return message;
    }

    public void setMessage(MessageBodyModel message) {
        this.message = message;
    }

    public Integer getDelayTimeLevel() {
        return delayTimeLevel;
    }

    public void setDelayTimeLevel(Integer delayTimeLevel) {
        this.delayTimeLevel = delayTimeLevel;
    }

    public SerializeTypeEnum getSerializeType() {
        return serializeType;
    }

    public void setSerializeType(SerializeTypeEnum serializeType) {
        this.serializeType = serializeType;
    }

    @Override
    public String toString() {
        return "MessageBuilder{" +
                "topic='" + topic + '\'' +
                ", tag='" + tag + '\'' +
                ", key='" + key + '\'' +
                ", message=" + message +
                ", delayTimeLevel=" + delayTimeLevel +
                ", serializeType=" + serializeType +
                '}';
    }
}
