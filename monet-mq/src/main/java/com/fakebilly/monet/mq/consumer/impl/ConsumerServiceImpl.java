package com.fakebilly.monet.mq.consumer.impl;

import cn.hutool.core.util.StrUtil;
import com.fakebilly.monet.mq.business.ConsumeBusinessLogicHandler;
import com.fakebilly.monet.mq.client.ConsumerClient;
import com.fakebilly.monet.mq.consumer.IConsumerService;
import com.fakebilly.monet.mq.exception.MQException;
import com.fakebilly.monet.mq.utils.TagsUtil;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * ConsumerServiceImpl
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class ConsumerServiceImpl implements IConsumerService {

    private static final Logger logger = LoggerFactory.getLogger(ConsumerServiceImpl.class);

    @Autowired
    private ConsumerClient consumerClient;

    private static final String ASTERISK = "*";
    private static final String PERCENT = "%";

    @Override
    public void receiveNormalMsg(String topic, Set<String> tags, String groupId, ConsumeBusinessLogicHandler logicHandler) throws MQException {
        String formatTag;
        if (StringUtils.isEmpty(groupId)) {
            formatTag = TagsUtil.formatTag(tags, true);
            if (ASTERISK.equals(formatTag)) {
                groupId = PERCENT;
            } else {
                groupId = formatTag.replace("||", "_");
            }
        } else {
            formatTag = TagsUtil.formatTag(tags, false);
        }
        String clusterName = consumerClient.getClusterName();
        DefaultMQPushConsumer consumer = consumerClient.getNormalMQPushConsumer(clusterName, groupId, topic, formatTag, false);
        consumerClient.startNormalConsumer(clusterName, consumer, topic, formatTag, logicHandler);
    }

    @Override
    public void receiveOrderMsg(String topic, Set<String> tags, String groupId,
                                ConsumeBusinessLogicHandler logicHandler) throws MQException {
        String formatTag;
        if (StrUtil.isBlank(groupId)) {
            formatTag = TagsUtil.formatTag(tags, true);
            if (ASTERISK.equals(formatTag)) {
                groupId = PERCENT;
            } else {
                groupId = formatTag.replace("||", "_");
            }
        } else {
            formatTag = TagsUtil.formatTag(tags, false);
        }
        String clusterName = consumerClient.getClusterName();
        DefaultMQPushConsumer consumer = consumerClient.getOrderlyMQPushConsumer(clusterName, groupId, topic, formatTag, false);
        consumerClient.startOrderConsumer(clusterName, consumer, topic, formatTag, logicHandler);
    }

}
