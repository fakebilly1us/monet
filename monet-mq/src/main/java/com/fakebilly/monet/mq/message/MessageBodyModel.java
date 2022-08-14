package com.fakebilly.monet.mq.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * MessageBodyModel
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class MessageBodyModel implements Serializable {

    private static final long serialVersionUID = -9017229456531511657L;

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

    private long msgCreateTime = System.currentTimeMillis();

    public MessageBodyModel() {
    }

    public MessageBodyModel(String id, Map<String, Object> messageBody) {
        super();
        this.id = id;
        this.messageBody = messageBody;
    }


    public void addMessageBody(String key, Object value) {
        if (null == messageBody) {
            messageBody = new HashMap<>();
        }
        messageBody.put(key, value);
    }


    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }


    public Map<String, Object> getMessageBody() {
        return messageBody;
    }


    public void setMessageBody(Map<String, Object> messageBody) {
        this.messageBody = messageBody;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public long getMsgCreateTime() {
        return msgCreateTime;
    }

    @Override
    public String toString() {
        return "MessageBodyModel{" +
                "id='" + id + '\'' +
                ", messageBody=" + messageBody +
                ", messageType=" + messageType +
                ", msgCreateTime=" + msgCreateTime +
                '}';
    }
}
