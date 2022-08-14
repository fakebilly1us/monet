package com.fakebilly.monet.mq.utils;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.fakebilly.monet.mq.constant.MqConfigConstant;
import com.fakebilly.monet.mq.enums.SerializeTypeEnum;
import com.fakebilly.monet.mq.message.MessageBodyModel;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.util.SerializationUtils;

/**
 * MessageUtil
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class MessageUtil {

    public static MessageBodyModel deserializeMessageBody(MessageExt msg) {
        String serializeType = msg.getUserProperty(MqConfigConstant.SERIALIZE_TYPE_KEY);
        SerializeTypeEnum serializeTypeEnum = null == SerializeTypeEnum.from(serializeType) ? SerializeTypeEnum.JSON_SERIALIZE : SerializeTypeEnum.from(serializeType);
        MessageBodyModel messageBodyModel;
        switch (serializeTypeEnum) {
            case JDK_SERIALIZE:
                try {
                    return (MessageBodyModel) SerializationUtils.deserialize(msg.getBody());
                } catch (Exception e) {
                    MessageBodyModel originMessageBodyModel = (MessageBodyModel) SerializationUtils.deserialize(msg.getBody());
                    messageBodyModel = new MessageBodyModel();
                    BeanUtil.copyProperties(originMessageBodyModel, messageBodyModel);
                    return messageBodyModel;
                }
            case JSON_SERIALIZE:
            default:
                return JSONObject.parseObject(msg.getBody(), MessageBodyModel.class);
        }
    }
}
