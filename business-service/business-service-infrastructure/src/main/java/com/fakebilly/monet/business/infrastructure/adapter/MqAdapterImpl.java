package com.fakebilly.monet.business.infrastructure.adapter;

import com.fakebilly.monet.business.domain.adapter.MqAdapter;
import com.fakebilly.monet.business.domain.mq.builder.MQMessageBuilder;
import com.fakebilly.monet.business.infrastructure.mq.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MqAdapter.MqAdapterImpl
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Component
public class MqAdapterImpl implements MqAdapter {

    @Autowired
    private Producer producer;

    @Override
    public void sendNormal(MQMessageBuilder messageBuilder) {
        producer.sendNormal(messageBuilder);
    }

    @Override
    public void sendOrder(MQMessageBuilder messageBuilder) {
        producer.sendOrder(messageBuilder);
    }

}
