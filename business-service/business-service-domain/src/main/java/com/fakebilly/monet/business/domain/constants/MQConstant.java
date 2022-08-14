package com.fakebilly.monet.business.domain.constants;

/**
 * MQConstant
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class MQConstant {

    /**
     * 消费者消息类型 normal 普通消息
     */
    public static final String MESSAGE_NORMAL = "normal";

    /**
     * 消费者消息类型 order 顺序消息
     */
    public static final String MESSAGE_ORDER = "order";

    /**
     * 生产者
     */
    public static final String TYPE_PRODUCER = "producer";

    /**
     * 消费者
     */
    public static final String TYPE_CONSUMER = "consumer";

    /**
     * topic
     */
    public static final String TOPIC_BUSINESS = "business-service";

    /**
     * tag
     */
    public static final String TAG_PRODUCER = "business-service-producer";

    /**
     * topic
     */
    public static final String TOPIC_BASIC = "basic-service";

    /**
     * tag
     */
    public static final String TAG_CONSUMER = "basic-service-producer";




}
