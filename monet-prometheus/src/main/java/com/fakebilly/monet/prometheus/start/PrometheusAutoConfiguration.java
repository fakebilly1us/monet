package com.fakebilly.monet.prometheus.start;

import com.fakebilly.monet.prometheus.config.MonetMonitorConfigInfo;
import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * PrometheusAutoConfiguration
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@ComponentScan({"com.fakebilly.monet.prometheus"})
@Configuration
public class PrometheusAutoConfiguration {

    @Autowired
    private MonetMonitorConfigInfo monetMonitorConfigInfo;

    @Bean
    @ConditionalOnMissingBean({MeterRegistryCustomizer.class})
    MeterRegistryCustomizer<MeterRegistry> configurer() {
        return (registry) -> {
            String[] args = {MonetMonitorConfigInfo.APPLICATION_NAME_KEY, monetMonitorConfigInfo.getApplicationName()};
            registry.config().commonTags(args);
        };
    }

    @Bean
    @ConditionalOnMissingBean({TimedAspect.class})
    TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
}
