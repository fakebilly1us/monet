package com.fakebilly.monet.mq.enums;

/**
 * ProducerGroupEnum
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public enum ProducerGroupEnum {

    /**
     * 普通消息组
     */
    NORMAL_PRODUCER_GROUP("normalPG", "普通消息组"),

    /**
     * 顺序消息组
     */
    ORDER_PRODUCER_GROUP("orderPG", "顺序消息组"),

    ;

    private String producerGroup;

    private String desc;

    ProducerGroupEnum(String producerGroup, String desc) {
        this.producerGroup = producerGroup;
        this.desc = desc;
    }

    public String getProducerGroup() {
        return producerGroup;
    }

    public String getDesc() {
        return desc;
    }


}
