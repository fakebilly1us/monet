package com.fakebilly.monet.prometheus.listener;

import com.fakebilly.monet.prometheus.config.MonetMonitorConfigInfo;
import com.fakebilly.monet.prometheus.event.AfterInitEvent;
import com.fakebilly.monet.prometheus.executor.PrometheusThreadFactory;
import com.fakebilly.monet.prometheus.factory.DiscoveryHandlerStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * AfterInitEventListener
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Component
public class AfterInitEventListener implements ApplicationListener<AfterInitEvent> {

    private static final long INITIAL_DELAY_DEFAULT = 5000;
    private static final String SCHEDULED_EXECUTOR_SERVICE_NAME = "MonetMonitorConfigScheduledThread";
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new PrometheusThreadFactory(SCHEDULED_EXECUTOR_SERVICE_NAME));

    @Autowired
    private MonetMonitorConfigInfo monetMonitorConfigInfo;

    @Override
    public void onApplicationEvent(AfterInitEvent event) {
        if (monetMonitorConfigInfo.discovery()) {
            scheduledExecutorService.scheduleWithFixedDelay(() -> {
                DiscoveryHandlerStrategyFactory.getHandler(monetMonitorConfigInfo.getDiscoveryType()).discovery();
            }, INITIAL_DELAY_DEFAULT, monetMonitorConfigInfo.getDiscoveryDelay(), TimeUnit.MILLISECONDS);

        }
    }

    @PreDestroy
    private void shutdown() {
        scheduledExecutorService.shutdown();
    }
}
