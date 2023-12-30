package com.fakebilly.monet.mq.client;

import com.fakebilly.monet.mq.business.ConsumeBusinessLogicHandler;
import com.fakebilly.monet.mq.config.MQConfig;
import com.fakebilly.monet.mq.config.MQConfigInfo;
import com.fakebilly.monet.mq.enums.ClusterNameEnum;
import com.fakebilly.monet.mq.enums.ConsumerGroupEnum;
import com.fakebilly.monet.mq.exception.MQException;
import com.fakebilly.monet.mq.utils.GroupNameUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ConsumerClient
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class ConsumerClient {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerClient.class);

    private static final int INITIAL_CAPACITY = 16;
    private static final ConcurrentHashMap<String, DefaultMQPushConsumer> NORMAL_SINGLETON_CONSUMERS = new ConcurrentHashMap<>(INITIAL_CAPACITY);
    private static final ConcurrentHashMap<String, DefaultMQPushConsumer> ORDERLY_SINGLETON_CONSUMERS = new ConcurrentHashMap<>(INITIAL_CAPACITY);

    /**
     * get DefaultMQPushConsumer normal
     * @param clusterName
     * @param groupId
     * @param topic
     * @param formatTag
     * @param originalGroupId
     * @return
     */
    public DefaultMQPushConsumer getNormalMQPushConsumer(String clusterName, String groupId, String topic, String formatTag, boolean originalGroupId) {
        String consumerMapKey = clusterName + "_" + topic + "_" + formatTag;
        if (NORMAL_SINGLETON_CONSUMERS.containsKey(consumerMapKey)) {
            return NORMAL_SINGLETON_CONSUMERS.get(consumerMapKey);
        }
        synchronized (ConsumerClient.class) {
            if (NORMAL_SINGLETON_CONSUMERS.containsKey(consumerMapKey)) {
                return NORMAL_SINGLETON_CONSUMERS.get(consumerMapKey);
            }
            DefaultMQPushConsumer consumer;
            if (originalGroupId) {
                consumer = getConsumerInstance(clusterName, groupId, topic);
            } else {
                consumer = getConsumerInstance(clusterName, ConsumerGroupEnum.NORMAL_CONSUMER_GROUP.getConsumerGroup() + "_" + groupId, topic);
            }
            return consumer;
        }
    }

    /**
     * get DefaultMQPushConsumer orderly
     * @param clusterName
     * @param groupId
     * @param topic
     * @param formatTag
     * @param originalGroupId
     * @return
     */
    public DefaultMQPushConsumer getOrderlyMQPushConsumer(String clusterName, String groupId, String topic, String formatTag, boolean originalGroupId) {
        String consumerMapKey = clusterName + "_" + topic + "_" + formatTag;
        if (ORDERLY_SINGLETON_CONSUMERS.containsKey(consumerMapKey)) {
            return ORDERLY_SINGLETON_CONSUMERS.get(consumerMapKey);
        }
        synchronized (ConsumerClient.class) {
            if (ORDERLY_SINGLETON_CONSUMERS.containsKey(consumerMapKey)) {
                return ORDERLY_SINGLETON_CONSUMERS.get(consumerMapKey);
            }
            DefaultMQPushConsumer consumer;
            if (originalGroupId) {
                consumer = getConsumerInstance(clusterName, groupId, topic);
            } else {
                consumer = getConsumerInstance(clusterName, ConsumerGroupEnum.ORDER_CONSUMER_GROUP.getConsumerGroup() + "_" + groupId, topic);
            }

            return consumer;
        }
    }

    /**
     * start
     * @param clusterName
     * @param consumer
     * @param topic
     * @param formatTag
     * @param logicHandler
     * @throws MQException
     */
    public void startNormalConsumer(String clusterName, DefaultMQPushConsumer consumer, String topic, String formatTag,
                                    final ConsumeBusinessLogicHandler logicHandler) throws MQException {
        try {
            consumer.subscribe(topic, formatTag);
            logger.info("topic [{}] subscribe to cluster [{}]", topic, consumer.getNamesrvAddr());
            consumer.registerMessageListener((MessageListenerConcurrently) (messageExts, context) -> {
                MessageExt messageExt = messageExts.get(0);
                try {
                    // 具体的业务逻辑处理
                    boolean flag = logicHandler.businessLogicExecute(messageExt);
                    if (!flag) {
                        logger.warn("receive normal message success, but business logic execute failed, cluster [{}], topic [{}], tags [{}], messageId [{}], keys [{}]",
                                consumer.getNamesrvAddr(), messageExt.getTopic(), messageExt.getTags(), messageExt.getMsgId(), messageExt.getKeys());
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                } catch (Exception e) {
                    logger.error("handle normal message failed, cluster [{}], topic [{}], tags [{}], messageId [{}], keys [{}], exception {}",
                            consumer.getNamesrvAddr(), messageExt.getTopic(), messageExt.getTags(), messageExt.getMsgId(), messageExt.getKeys(), e);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;

                }
                logger.info("handle normal message success, cluster [{}], topic [{}], tags [{}], messageId [{}], keys [{}]",
                        consumer.getNamesrvAddr(), messageExt.getTopic(), messageExt.getTags(), messageExt.getMsgId(), messageExt.getKeys());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            });
            // Consumer对象在使用之前必须要调用start初始化，初始化一次即可
            consumer.start();
            String consumerMapKey = clusterName + "_" + topic + "_" + formatTag;
            NORMAL_SINGLETON_CONSUMERS.put(consumerMapKey, consumer);
        } catch (MQClientException e) {
            logger.error(e.getMessage(), e);
            throw new MQException(e);
        }
    }

    /**
     * start
     * @param clusterName
     * @param consumer
     * @param topic
     * @param formatTag
     * @param logicHandler
     * @throws MQException
     */
    public void startOrderConsumer(String clusterName, DefaultMQPushConsumer consumer, String topic, String formatTag,
                                   final ConsumeBusinessLogicHandler logicHandler) throws MQException {
        try {
            consumer.subscribe(topic, formatTag);
            logger.info("topic [{}] subscribe to cluster [{}]", topic, consumer.getNamesrvAddr());
            consumer.registerMessageListener((MessageListenerOrderly) (messageExts, context) -> {
                MessageExt messageExt = messageExts.get(0);
                try {
                    // 具体的业务逻辑处理
                    boolean flag = logicHandler.businessLogicExecute(messageExt);
                    if (!flag) {
                        logger.warn("receive orderly message success, but business logic execute failed, cluster [{}], topic [{}], tags [{}], messageId [{}], keys [{}]",
                                consumer.getNamesrvAddr(), messageExt.getTopic(), messageExt.getTags(), messageExt.getMsgId(), messageExt.getKeys());
                        return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                    }
                } catch (Exception e) {
                    logger.error("handle orderly message failed, cluster [{}], topic [{}], tags [{}], messageId [{}], keys [{}], exception {}",
                            consumer.getNamesrvAddr(), messageExt.getTopic(), messageExt.getTags(), messageExt.getMsgId(), messageExt.getKeys(), e);
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
                logger.info("handle orderly message success, cluster [{}], topic [{}], tags [{}], messageId [{}], keys [{}]",
                        consumer.getNamesrvAddr(), messageExt.getTopic(), messageExt.getTags(), messageExt.getMsgId(), messageExt.getKeys());
                return ConsumeOrderlyStatus.SUCCESS;
            });
            // Consumer对象在使用之前必须要调用start初始化，初始化一次即可
            consumer.start();
            String consumerMapKey = clusterName + "_" + topic + "_" + formatTag;
            ORDERLY_SINGLETON_CONSUMERS.put(consumerMapKey, consumer);
        } catch (MQClientException e) {
            logger.error(e.getMessage(), e);
            throw new MQException(e);
        }
    }

    private DefaultMQPushConsumer getConsumerInstance(String clusterName, String group, String topic) throws MQException {
        final DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(GroupNameUtil.getGroupName(group, topic));
        consumerConfig(clusterName, consumer);
        return consumer;
    }

    private void consumerConfig(String clusterName, DefaultMQPushConsumer consumer) throws MQException {
        ClusterNameEnum clusterNameEnum = ClusterNameEnum.from(clusterName);
        if (clusterNameEnum == null) {
            throw new MQException("cluster name [" + clusterName + "] is invalid!");
        }
        MQConfigInfo configInfo = MQConfig.getConsumeMqMQConfigInfo(clusterNameEnum);
        if (null == configInfo) {
            throw new MQException("Consumer Config Cannot be Found");
        }
        consumer.setNamesrvAddr(configInfo.getNamesrvAddr());
        consumer.setInstanceName(GroupNameUtil.buildInstanceName(consumer.getConsumerGroup()));
        // 消费失败最大重试次数
        consumer.setMaxReconsumeTimes(configInfo.getMaxReconsumeTimes());
        // Consumer 启动后，从什么位 置开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

    }

    /**
     * get clusterName
     * @return
     */
    public String getClusterName() {
        return ClusterNameEnum.CENTER_CLUSTER.getCode();
    }


}
