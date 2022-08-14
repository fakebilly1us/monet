package com.fakebilly.monet.prometheus.rpc;

import cn.hutool.core.map.MapUtil;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.BaseUnits;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.apache.dubbo.common.extension.ExtensionLoader;
import org.apache.dubbo.common.store.DataStore;
import org.apache.dubbo.remoting.Constants;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * MonetDubboMetrics
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class MonetDubboMetrics implements MeterBinder, AutoCloseable {

    private static final String METRIC_NAME_DUBBO_THREAD_POOL_CORE_SIZE = "dubbo.thread.pool.core.size";
    private static final String METRIC_NAME_DUBBO_THREAD_POOL_LARGEST_SIZE = "dubbo.thread.pool.largest.size";
    private static final String METRIC_NAME_DUBBO_THREAD_POOL_MAX_SIZE = "dubbo.thread.pool.max.size";
    private static final String METRIC_NAME_DUBBO_THREAD_POOL_ACTIVE_SIZE = "dubbo.thread.pool.active.size";
    private static final String METRIC_NAME_DUBBO_THREAD_POOL_THREAD_COUNT = "dubbo.thread.pool.thread.count";
    private static final String METRIC_NAME_DUBBO_THREAD_POOL_QUEUE_SIZE = "dubbo.thread.pool.queue.size";
    private static final String METRIC_NAME_DUBBO_THREAD_POOL_TASKCOUNT = "dubbo.thread.pool.taskCount";
    private static final String METRIC_NAME_DUBBO_THREAD_POOL_COMPLETEDTASKCOUNT = "dubbo.thread.pool.completedTaskCount";

    private final Iterable<Tag> tags;

    public MonetDubboMetrics() {
        this(Collections.emptyList());
    }

    public MonetDubboMetrics(Iterable<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public void bindTo(MeterRegistry registry) {
        registerThreadPoolExecutorMetrics(registry);
    }

    private void registerThreadPoolExecutorMetrics(MeterRegistry registry) {
        DataStore dataStore = ExtensionLoader.getExtensionLoader(DataStore.class).getDefaultExtension();
        if (null == dataStore) {
            return;
        }
        Map<String, Object> executorMap = dataStore.get(Constants.EXECUTOR_SERVICE_COMPONENT_KEY);
        if (MapUtil.isEmpty(executorMap)) {
            return;
        }
        executorMap.forEach((k, v) -> {
            ExecutorService executor = (ExecutorService) v;
            if (executor instanceof ThreadPoolExecutor) {
                ThreadPoolExecutor tp = (ThreadPoolExecutor) executor;
                Gauge.builder(METRIC_NAME_DUBBO_THREAD_POOL_CORE_SIZE, tp, ThreadPoolExecutor::getCorePoolSize)
                        .tags(tags)
                        .baseUnit(BaseUnits.THREADS)
                        .register(registry);
                Gauge.builder(METRIC_NAME_DUBBO_THREAD_POOL_LARGEST_SIZE, tp, ThreadPoolExecutor::getLargestPoolSize)
                        .tags(tags)
                        .baseUnit(BaseUnits.THREADS)
                        .register(registry);
                Gauge.builder(METRIC_NAME_DUBBO_THREAD_POOL_MAX_SIZE, tp, ThreadPoolExecutor::getMaximumPoolSize)
                        .tags(tags)
                        .baseUnit(BaseUnits.THREADS)
                        .register(registry);
                Gauge.builder(METRIC_NAME_DUBBO_THREAD_POOL_ACTIVE_SIZE, tp, ThreadPoolExecutor::getActiveCount)
                        .tags(tags)
                        .baseUnit(BaseUnits.THREADS)
                        .register(registry);
                Gauge.builder(METRIC_NAME_DUBBO_THREAD_POOL_THREAD_COUNT, tp, ThreadPoolExecutor::getPoolSize)
                        .tags(tags)
                        .baseUnit(BaseUnits.THREADS)
                        .register(registry);
                Gauge.builder(METRIC_NAME_DUBBO_THREAD_POOL_QUEUE_SIZE, tp, (e) -> e.getQueue().size())
                        .tags(tags)
                        .baseUnit(BaseUnits.THREADS)
                        .register(registry);
                Gauge.builder(METRIC_NAME_DUBBO_THREAD_POOL_TASKCOUNT, tp, ThreadPoolExecutor::getTaskCount)
                        .tags(tags)
                        .baseUnit(BaseUnits.THREADS)
                        .register(registry);
                Gauge.builder(METRIC_NAME_DUBBO_THREAD_POOL_COMPLETEDTASKCOUNT, tp, ThreadPoolExecutor::getCompletedTaskCount)
                        .tags(tags)
                        .baseUnit(BaseUnits.THREADS)
                        .register(registry);
            }
        });
    }


    @Override
    public void close() {

    }

}
