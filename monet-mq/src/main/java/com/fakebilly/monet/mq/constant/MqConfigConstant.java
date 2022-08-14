package com.fakebilly.monet.mq.constant;

/**
 * MqConfigConstant
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class MqConfigConstant {

    public static final String NAMESRV_ADDR = "rocketmq.namesrvAddr";

    public static final String RECONSUME_TIMES = "rocketmq.reconsumerTimes";

    public static final String RECONSUME_TIMES_DEFAULT = "6";

    public static final String MAX_RECONSUME_TIMES = "rocketmq.maxReconsumeTimes";

    public static final String MAX_RECONSUME_TIMES_DEFAULT = "6";

    public static final String SEND_MSG_TIMEOUT = "rocketmq.sendMsgTimeout";

    public static final String SEND_MSG_TIMEOUT_DEFAULT = "3000";

    public static final String RETRYTIMES_WHEN_SEND_FAILED = "rocketmq.retryTimesWhenSendFailed";

    public static final String RETRYTIMES_WHEN_SEND_FAILED_DEFAULT = "3";

    public static final String SERIALIZE_TYPE_KEY = "serialize_type";

}
