package com.fakebilly.monet.business.domain.mq.builder;

import cn.hutool.core.collection.CollectionUtil;
import com.fakebilly.monet.business.domain.enums.CodeEnum;
import com.fakebilly.monet.business.domain.utils.Assert;
import com.fakebilly.monet.mq.enums.SerializeTypeEnum;
import com.fakebilly.monet.mq.message.MessageBodyModel;
import com.fakebilly.monet.mq.message.MessageBuilder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * MQMessageBuilder
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class MQMessageBuilder {

    /**
     * topic
     */
    private String topic;
    /**
     * tag
     */
    private String tag;
    /**
     * key
     */
    private String key;
    /**
     * delayTimeLevel
     */
    private Integer delayTimeLevel;

    private MQMessage MQMessage;

    private List<MQMessage> MQMessageList;

    /**
     * 序列化模式
     */
    private SerializeType serializeType;


    public SerializeTypeEnum getOutSerializeType() {
        return SerializeType.out(serializeType);
    }

    public static MQMessageBuilder create() {
        return new MQMessageBuilder();
    }

    protected MQMessageBuilder() {
    }

    public MQMessageBuilder topic(String topic) {
        Assert.notBlank(topic, CodeEnum.INVALID, "topic 不能为空");
        this.topic = topic;
        return this;
    }

    public MQMessageBuilder tag(String tag) {
        Assert.notBlank(tag, CodeEnum.INVALID, "tag 不能为空");
        this.tag = tag;
        return this;
    }

    public MQMessageBuilder key(String key) {
        Assert.notBlank(key, CodeEnum.INVALID, "key 不能为空");
        this.key = key;
        return this;
    }

    public MQMessageBuilder delayTimeLevel(Integer delayTimeLevel) {
        this.delayTimeLevel = delayTimeLevel;
        return this;
    }

    public MQMessageBuilder message(MQMessage MQMessage) {
        Assert.isTrue(CollectionUtil.isEmpty(MQMessageList), CodeEnum.INVALID, "message messageList 不可同时使用");
        this.MQMessage = MQMessage;
        return this;
    }

    public MQMessageBuilder messageList(List<MQMessage> MQMessageList) {
        Assert.isTrue(null == MQMessage, CodeEnum.INVALID, "message messageList 不可同时使用");
        this.MQMessageList = MQMessageList;
        return this;
    }

    public MQMessageBuilder serializeType(SerializeType serializeType) {
        this.serializeType = serializeType;
        return this;
    }

    @SuppressWarnings("deprecation")
    public MessageBuilder buildNormal() {
        return MessageBuilder.create().topic(topic)
                .tag(tag)
                .key(key)
                .message(null == MQMessage ? null : MQMessage.toMessageBodyModel())
                .delayTimeLevel(delayTimeLevel);
    }

    @SuppressWarnings("deprecation")
    public List<MessageBuilder> buildOrder() {
        List<MessageBuilder> builderList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(MQMessageList)) {
            MQMessageList.forEach(msg -> {
                builderList.add(
                        MessageBuilder.create().topic(topic)
                                .tag(tag)
                                .key(key)
                                .message(msg.toMessageBodyModel())
                                .delayTimeLevel(delayTimeLevel)
                );
            });
        }
        return builderList;
    }

    @Override
    public String toString() {
        return "MQMessageBuilder{" +
                "topic='" + topic + '\'' +
                ", tag='" + tag + '\'' +
                ", key='" + key + '\'' +
                ", delayTimeLevel=" + delayTimeLevel +
                ", message=" + MQMessage +
                ", messageList=" + MQMessageList +
                ", serializeType=" + serializeType +
                '}';
    }

    @Data
    public static class MQMessage {
        /**
         * 业务主键
         */
        private String id;
        /**
         * 消息体需要传递的数据
         */
        private Map<String, Object> messageBody;
        /**
         * 消息类型
         */
        private Integer messageType;

        public MQMessage() {

        }

        public MQMessage(Map<String, Object> messageBody) {
            super();
            this.messageBody = messageBody;
        }

        public MQMessage(String id, Map<String, Object> messageBody) {
            super();
            this.id = id;
            this.messageBody = messageBody;
        }

        public MQMessage(String id, Map<String, Object> messageBody, Integer messageType) {
            super();
            this.id = id;
            this.messageBody = messageBody;
            this.messageType = messageType;
        }

        public MessageBodyModel toMessageBodyModel() {
            MessageBodyModel messageBodyModel = new MessageBodyModel(id, messageBody);
            messageBodyModel.setMessageType(messageType);
            return messageBodyModel;
        }
    }

    public enum SerializeType {
        /**
         * JDK序列化
         */
        JDK_SERIALIZE("jdk_serialize", "JDK序列化"),
        /**
         * JSON序列化
         */
        JSON_SERIALIZE("json_serialize", "JSON序列化");

        private String code;

        private String desc;

        SerializeType(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public static SerializeType getEnum(String code) {
            for (SerializeType type : values()) {
                if (type.code.equals(code)) {
                    return type;
                }
            }
            return null;
        }

        public static SerializeTypeEnum out(SerializeType type) {
            if (null == type) {
                return null;
            }
            SerializeTypeEnum target = null;
            switch (type) {
                case JDK_SERIALIZE:
                    target = SerializeTypeEnum.JDK_SERIALIZE;
                    break;
                case JSON_SERIALIZE:
                    target = SerializeTypeEnum.JSON_SERIALIZE;
                    break;
                default:
                    break;
            }
            return target;
        }


    }


}
