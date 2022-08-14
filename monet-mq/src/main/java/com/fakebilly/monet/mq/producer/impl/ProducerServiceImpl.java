package com.fakebilly.monet.mq.producer.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.fakebilly.monet.mq.client.ProducerClient;
import com.fakebilly.monet.mq.constant.MqConfigConstant;
import com.fakebilly.monet.mq.enums.SerializeTypeEnum;
import com.fakebilly.monet.mq.exception.MQException;
import com.fakebilly.monet.mq.message.MessageBuilder;
import com.fakebilly.monet.mq.producer.IProducerService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * ProducerServiceImpl
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class ProducerServiceImpl implements IProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerServiceImpl.class);

    @Autowired
    private ProducerClient producerClient;

    private final ConcurrentMap<String, DefaultMQProducer> normalSingletonProducers = new ConcurrentHashMap<>();
    private final ConcurrentMap<String, DefaultMQProducer> orderlySingletonProducers = new ConcurrentHashMap<>();

    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(r -> new Thread(r, "ProducerServiceImplScheduledThread"));

    @Override
    public boolean sendNormalMsg(MessageBuilder messageBuilder) throws MQException {
        return sendNormalMsg(messageBuilder, SerializeTypeEnum.JSON_SERIALIZE);
    }

    @Override
    public boolean sendNormalMsg(MessageBuilder messageBuilder, SerializeTypeEnum serializeType) throws MQException {
        if (null == messageBuilder || null == messageBuilder.getMessage()) {
            throw new MQException("the messageBody cannot be null");
        }
        DefaultMQProducer producer;
        SendResult sendResult;
        Message message = new Message();
        String namesrvAddr = "";
        try {
            String clusterName = producerClient.getClusterName();
            producer = producerClient.getNormalMQProducer(clusterName);
            namesrvAddr = producer.getNamesrvAddr();
            message = messageBuilder.build(serializeType);
            message.putUserProperty(MqConfigConstant.SERIALIZE_TYPE_KEY, serializeType.getCode());
            sendResult = producer.send(message);
            if (sendResult != null) {
                logger.info("send normal message success, namesrvAddr [{}], topic [{}], tags [{}], messageId [{}], keys [{}]",
                        namesrvAddr, message.getTopic(), message.getTags(), sendResult.getMsgId(), message.getKeys());
            }
            return true;
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            logger.error("send normal message failed, namesrvAddr [{}], topic [{}], tags [{}], keys [{}], e {}",
                    namesrvAddr, message.getTopic(), message.getTags(), message.getKeys(), e);
            return false;
        }
    }

    @Override
    public boolean sendOrderMsg(List<MessageBuilder> messageBuilderList) throws MQException {
        return sendOrderMsg(messageBuilderList, SerializeTypeEnum.JSON_SERIALIZE);
    }

    @Override
    public boolean sendOrderMsg(List<MessageBuilder> messageBuilderList, SerializeTypeEnum serializeType) throws MQException {
        if (CollectionUtil.isEmpty(messageBuilderList)) {
            throw new MQException("the messageBody cannot be null");
        }
        SendResult sendResult;
        DefaultMQProducer producer;
        String topic = "";
        String namesrvAddr = "";
        String tags = "";
        String key = "";
        try {
            MessageBuilder originalMessageBuilder = messageBuilderList.get(0);
            topic = originalMessageBuilder.getTopic();
            String clusterName = producerClient.getClusterName();
            producer = producerClient.getOrderlyMQProducer(clusterName);
            namesrvAddr = producer.getNamesrvAddr();
            // 按照消息的先后顺序批量发送
            for (MessageBuilder msgBuilder : messageBuilderList) {
                tags = msgBuilder.getTag();
                key = msgBuilder.getKey();
                Message msg = msgBuilder.build(serializeType);
                msg.putUserProperty(MqConfigConstant.SERIALIZE_TYPE_KEY, serializeType.getCode());
                // 发送顺序消息，保证消息走同一队列（在分布式情况下走同一服务器下的同一队列）;
                // 对key取模运算，key 相同的会发到同一个队列中
                sendResult = producer.send(msg, (mqs, msg1, arg) -> {
                    Long id = (Long) arg;
                    long index = id % mqs.size();
                    return mqs.get((int) index);
                }, Long.parseLong(msg.getKeys()));
                if (sendResult != null) {
                    logger.info("send orderly message success, namesrvAddr [{}], topic [{}], tags [{}], messageId [{}], keys [{}]",
                            namesrvAddr, topic, tags, sendResult.getMsgId(), key);
                }
            }
            return true;
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            logger.error("send orderly message fail, namesrvAddr [{}], topic [{}], tags [{}], keys [{}], e {}",
                    namesrvAddr, topic, tags, key, e);
            return false;
        }
    }

    @Override
    public void sendOnewayMsg(MessageBuilder messageBuilder) throws MQException {
        sendOnewayMsg(messageBuilder, SerializeTypeEnum.JSON_SERIALIZE);
    }

    @Override
    public void sendOnewayMsg(MessageBuilder messageBuilder, SerializeTypeEnum serializeType) throws MQException {
        if (null == messageBuilder || null == messageBuilder.getMessage()) {
            throw new MQException("the messageBody cannot be null");
        }
        DefaultMQProducer producer;
        Message message = new Message();
        String namesrvAddr = "";
        try {
            String clusterName = producerClient.getClusterName();
            producer = producerClient.getNormalMQProducer(clusterName);
            namesrvAddr = producer.getNamesrvAddr();
            message = messageBuilder.build(serializeType);
            message.putUserProperty(MqConfigConstant.SERIALIZE_TYPE_KEY, serializeType.getCode());
            producer.sendOneway(message);
            logger.info("send oneway message success, namesrvAddr [{}], topic [{}], tags [{}], keys [{}]",
                    namesrvAddr, message.getTopic(), message.getTags(), message.getKeys());
        } catch (MQClientException | RemotingException | InterruptedException e) {
            logger.error("send oneway message failed, namesrvAddr [{}], topic [{}], tags [{}], keys [{}], e {}",
                    namesrvAddr, message.getTopic(), message.getTags(), message.getKeys(), e);
        }
    }

    @Override
    public boolean sendBatchMsg(List<MessageBuilder> messageBuilderList) throws MQException {
        if (CollectionUtil.isEmpty(messageBuilderList)) {
            throw new MQException("the messageBody cannot be null");
        }
        DefaultMQProducer producer;
        String namesrvAddr = "";
        try {
            List<Message> messageList = new ArrayList<>(messageBuilderList.size());
            for (MessageBuilder messageBuilder : messageBuilderList) {
                Message message = messageBuilder.build();
                messageList.add(message);
            }
            String clusterName = producerClient.getClusterName();
            producer = producerClient.getNormalMQProducer(clusterName);
            namesrvAddr = producer.getNamesrvAddr();
            producer.send(messageList);
            logger.info("send batch message success, namesrvAddr [{}], messageBuilderList [{}]", namesrvAddr, JSON.toJSONString(messageBuilderList));
            return true;
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            logger.error("send batch message failed, namesrvAddr [{}], messageBuilderList [{}], e {}", namesrvAddr, JSON.toJSONString(messageBuilderList), e);
        }
        return false;
    }

    @Override
    public boolean sendBatchMsg(List<MessageBuilder> messageBuilderList, long timeout) throws MQException {
        if (CollectionUtil.isEmpty(messageBuilderList)) {
            throw new MQException("the messageBody cannot be null");
        }
        DefaultMQProducer producer;
        String namesrvAddr = "";
        try {
            List<Message> messageList = new ArrayList<>(messageBuilderList.size());
            for (MessageBuilder messageBuilder : messageBuilderList) {
                Message message = messageBuilder.build();
                messageList.add(message);
            }
            String clusterName = producerClient.getClusterName();
            producer = producerClient.getNormalMQProducer(clusterName);
            namesrvAddr = producer.getNamesrvAddr();
            producer.send(messageList, timeout);
            logger.info("send batch message timeout success, namesrvAddr [{}], messageBuilderList [{}], timeout [{}]", namesrvAddr, JSON.toJSONString(messageBuilderList), timeout);
            return true;
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            logger.error("send batch message timeout failed, namesrvAddr [{}], messageBuilderList [{}], timeout [{}], e {}", namesrvAddr, JSON.toJSONString(messageBuilderList), timeout, e);
        }
        return false;
    }

}
