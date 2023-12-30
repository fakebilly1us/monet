package com.fakebilly.monet.mq.enums;

/**
 * ConsumerGroupEnum
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public enum ConsumerGroupEnum {

    /**
     * 普通消息组
     */
    NORMAL_CONSUMER_GROUP("normalCG", "普通消息组"),

    /**
     * 顺序消息组
     */
    ORDER_CONSUMER_GROUP("orderCG", "顺序消息组"),

    ;


    private String consumerGroup;

    private String desc;

    ConsumerGroupEnum(String consumerGroup, String desc) {
        this.consumerGroup = consumerGroup;
        this.desc = desc;
    }


    public String getConsumerGroup() {
        return consumerGroup;
    }


    public String getDesc() {
        return desc;
    }

}
