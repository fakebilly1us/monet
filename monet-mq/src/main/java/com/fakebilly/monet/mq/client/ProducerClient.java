package com.fakebilly.monet.mq.client;

import com.fakebilly.monet.mq.config.MQConfig;
import com.fakebilly.monet.mq.config.MQConfigInfo;
import com.fakebilly.monet.mq.enums.ClusterNameEnum;
import com.fakebilly.monet.mq.enums.ProducerGroupEnum;
import com.fakebilly.monet.mq.exception.MQException;
import com.fakebilly.monet.mq.executor.MQThreadFactory;
import com.fakebilly.monet.mq.producer.impl.ProducerServiceImpl;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * ProducerClient
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class ProducerClient {

    private static final Logger logger = LoggerFactory.getLogger(ProducerClient.class);

    private static final int INITIAL_CAPACITY = 16;
    private static final ConcurrentMap<String, DefaultMQProducer> NORMAL_SINGLETON_PRODUCERS = new ConcurrentHashMap<>(INITIAL_CAPACITY);
    private static final ConcurrentMap<String, DefaultMQProducer> ORDERLY_SINGLETON_PRODUCERS = new ConcurrentHashMap<>(INITIAL_CAPACITY);

    private static final long DELAY_DEFAULT = 5000;
    private static final String SCHEDULED_EXECUTOR_SERVICE_NAME = "ProducerClientScheduledThread";
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new MQThreadFactory(SCHEDULED_EXECUTOR_SERVICE_NAME));

    /**
     * get DefaultMQProducer normal
     * @param clusterName
     * @return
     * @throws MQException
     */
    public DefaultMQProducer getNormalMQProducer(String clusterName) {
        pull();
        if (NORMAL_SINGLETON_PRODUCERS.containsKey(clusterName)) {
            return NORMAL_SINGLETON_PRODUCERS.get(clusterName);
        }
        synchronized (ProducerServiceImpl.class) {
            if (NORMAL_SINGLETON_PRODUCERS.containsKey(clusterName)) {
                return NORMAL_SINGLETON_PRODUCERS.get(clusterName);
            }
            DefaultMQProducer producer = getNormalInstance(clusterName);
            NORMAL_SINGLETON_PRODUCERS.put(clusterName, producer);
            return producer;
        }
    }

    /**
     * get DefaultMQProducer order
     * @param clusterName
     * @return
     * @throws MQException
     */
    public DefaultMQProducer getOrderlyMQProducer(String clusterName) {
        pull();
        if (ORDERLY_SINGLETON_PRODUCERS.containsKey(clusterName)) {
            return ORDERLY_SINGLETON_PRODUCERS.get(clusterName);
        }
        synchronized (ProducerServiceImpl.class) {
            if (ORDERLY_SINGLETON_PRODUCERS.containsKey(clusterName)) {
                return ORDERLY_SINGLETON_PRODUCERS.get(clusterName);
            }
            DefaultMQProducer producer = getOrderInstance(clusterName);
            ORDERLY_SINGLETON_PRODUCERS.put(clusterName, producer);
            return producer;
        }
    }

    private DefaultMQProducer getNormalInstance(String clusterName) throws MQException {
        DefaultMQProducer normalProducer = new DefaultMQProducer(ProducerGroupEnum.NORMAL_PRODUCER_GROUP.getProducerGroup());
        producerConfig(clusterName, normalProducer);
        return normalProducer;
    }

    private DefaultMQProducer getOrderInstance(String clusterName) throws MQException {
        DefaultMQProducer orderProducer = new DefaultMQProducer(ProducerGroupEnum.ORDER_PRODUCER_GROUP.getProducerGroup());
        producerConfig(clusterName, orderProducer);
        return orderProducer;
    }

    private void producerConfig(String clusterName, DefaultMQProducer producer) throws MQException {
        ClusterNameEnum clusterNameEnum = ClusterNameEnum.from(clusterName);
        if (clusterNameEnum == null) {
            throw new MQException("cluster name [" + clusterName + "] is invalid");
        }
        MQConfigInfo configInfo = MQConfig.getProducerMqMQConfigInfo(clusterNameEnum);
        if (null == configInfo) {
            throw new MQException("Producer config cannot be found");
        }
        producer.setNamesrvAddr(configInfo.getNamesrvAddr());
        producer.setSendMsgTimeout(configInfo.getSendMsgTimeout());
        producer.setRetryTimesWhenSendFailed(configInfo.getRetryTimesWhenSendFailed());
        try {
            producer.start();
        } catch (MQClientException e) {
            logger.error("ProducerClient start failed ", e);
            throw new MQException("ProducerClient start failed");
        }
    }

    /**
     * pull for update
     */
    private void pull() {
        DefaultMQProducer defaultNormalMQProducer = NORMAL_SINGLETON_PRODUCERS.get(ClusterNameEnum.CENTER_CLUSTER.getCode());
        DefaultMQProducer defaultOrderlyMQProducer = ORDERLY_SINGLETON_PRODUCERS.get(ClusterNameEnum.CENTER_CLUSTER.getCode());
        MQConfigInfo configInfo = MQConfig.getConfigInfo();
        if (defaultNormalMQProducer != null && configInfo != null && !defaultNormalMQProducer.getNamesrvAddr().equals(configInfo.getNamesrvAddr())) {
            NORMAL_SINGLETON_PRODUCERS.remove(ClusterNameEnum.CENTER_CLUSTER.getCode());
            scheduledExecutorService.schedule(defaultNormalMQProducer::shutdown, DELAY_DEFAULT, TimeUnit.MILLISECONDS);
        }
        if (defaultOrderlyMQProducer != null && configInfo != null && !defaultOrderlyMQProducer.getNamesrvAddr().equals(configInfo.getNamesrvAddr())) {
            ORDERLY_SINGLETON_PRODUCERS.remove(ClusterNameEnum.CENTER_CLUSTER.getCode());
            scheduledExecutorService.schedule(defaultOrderlyMQProducer::shutdown, DELAY_DEFAULT, TimeUnit.MILLISECONDS);
        }

    }

    /**
     * get clusterName
     * @return
     */
    public String getClusterName() {
        return ClusterNameEnum.CENTER_CLUSTER.getCode();
    }

    @PreDestroy
    private void shutdown() {
        scheduledExecutorService.shutdown();
    }
}
