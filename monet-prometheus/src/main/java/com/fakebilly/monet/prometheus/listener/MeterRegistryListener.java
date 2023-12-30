package com.fakebilly.monet.prometheus.listener;

import com.fakebilly.monet.prometheus.config.MonetMonitorConfig;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * MeterRegistryListener
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Component
public class MeterRegistryListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(MeterRegistryListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("MeterRegistryListener.onApplicationEvent");
        ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
        PrometheusMeterRegistry meterRegistry = (PrometheusMeterRegistry) applicationContext.getBean(MeterRegistry.class);
        MonetMonitorConfig.init(applicationContext, meterRegistry);
    }
}
