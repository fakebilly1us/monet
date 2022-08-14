package com.fakebilly.monet.prometheus.web;

import cn.hutool.core.collection.CollectionUtil;
import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.binder.BaseUnits;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.lang.Nullable;
import io.undertow.Undertow;
import io.undertow.server.ConnectorStatistics;
import io.undertow.server.session.SessionManagerStatistics;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.boot.web.embedded.undertow.UndertowWebServer;
import org.springframework.util.ReflectionUtils;
import org.xnio.management.XnioWorkerMXBean;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * MonetUndertowMetrics
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class MonetUndertowMetrics implements MeterBinder, AutoCloseable {

    public static final String UNDERTOW_METRIC_NAME = "undertow";

    private static final String METRIC_NAME_X_WORK_WORKER_POOL_CORE_SIZE = "undertow.xwork.worker.pool.core.size";
    private static final String METRIC_NAME_X_WORK_WORKER_POOL_MAX_SIZE = "undertow.xwork.worker.pool.max.size";
    private static final String METRIC_NAME_X_WORK_WORKER_POOL_SIZE = "undertow.xwork.worker.pool.size";
    private static final String METRIC_NAME_X_WORK_WORKER_THREAD_BUSY_COUNT = "undertow.xwork.worker.thread.busy.count";
    private static final String METRIC_NAME_X_WORK_IO_THREAD_COUNT = "undertow.xwork.io.thread.count";
    private static final String METRIC_NAME_X_WORK_WORKER_QUEUE_SIZE = "undertow.xwork.worker.queue.size";

    private static final String METRIC_NAME_CONNECTORS_REQUESTS_COUNT = "undertow.connectors.requests.count";
    private static final String METRIC_NAME_CONNECTORS_REQUESTS_ERROR_COUNT = "undertow.connectors.requests.error.count";
    private static final String METRIC_NAME_CONNECTORS_REQUESTS_ACTIVE = "undertow.connectors.requests.active";
    private static final String METRIC_NAME_CONNECTORS_REQUESTS_ACTIVE_MAX = "undertow.connectors.requests.active.max";
    private static final String METRIC_NAME_CONNECTORS_BYTES_SENT = "undertow.connectors.bytes.sent";
    private static final String METRIC_NAME_CONNECTORS_BYTES_RECEIVED = "undertow.connectors.bytes.received";
    private static final String METRIC_NAME_CONNECTORS_PROCESSING_TIME = "undertow.connectors.processing.time";
    private static final String METRIC_NAME_CONNECTORS_PROCESSING_TIME_MAX = "undertow.connectors.processing.time.max";
    private static final String METRIC_NAME_CONNECTORS_CONNECTIONS_ACTIVE = "undertow.connectors.connections.active";
    private static final String METRIC_NAME_CONNECTORS_CONNECTIONS_ACTIVE_MAX = "undertow.connectors.connections.active.max";

    private static final String METRIC_NAME_SESSIONS_ACTIVE_MAX = "undertow.sessions.active.max";
    private static final String METRIC_NAME_SESSIONS_ACTIVE_CURRENT = "undertow.sessions.active.current";
    private static final String METRIC_NAME_SESSIONS_CREATED = "undertow.sessions.created";
    private static final String METRIC_NAME_SESSIONS_EXPIRED = "undertow.sessions.expired";
    private static final String METRIC_NAME_SESSIONS_REJECTED = "undertow.sessions.rejected";
    private static final String METRIC_NAME_SESSIONS_ALIVE_MAX = "undertow.sessions.alive.max";

    private final XnioWorkerMXBean xnioWorkerMXBean;
    private final List<Undertow.ListenerInfo> listenerInfoList;
    private final SessionManagerStatistics statistics;

    private final Iterable<Tag> tags;

    public MonetUndertowMetrics(Iterable<Tag> tags, @Nullable UndertowWebServer undertowWebServer) {
        this.tags = tags;
        if (null != undertowWebServer) {
            Undertow undertow = buildUndertow(undertowWebServer);
            this.xnioWorkerMXBean = undertow.getWorker().getMXBean();
            this.listenerInfoList = undertow.getListenerInfo();
            if (undertowWebServer instanceof UndertowServletWebServer) {
                this.statistics = ((UndertowServletWebServer) undertowWebServer).getDeploymentManager()
                        .getDeployment()
                        .getSessionManager()
                        .getStatistics();
            } else {
                this.statistics = null;
            }
        } else {
            this.xnioWorkerMXBean = null;
            this.listenerInfoList = null;
            this.statistics = null;
        }
    }

    @Override
    public void bindTo(MeterRegistry registry) {
        registerXWorker(registry);
        registerConnectorStatistics(registry);
        registerSessionStatistics(registry);
    }

    private void registerXWorker(MeterRegistry registry) {
        Gauge.builder(METRIC_NAME_X_WORK_WORKER_POOL_CORE_SIZE, xnioWorkerMXBean, XnioWorkerMXBean::getCoreWorkerPoolSize)
                .tags(tags)
                .tag("name", xnioWorkerMXBean.getName())
                .register(registry);
        Gauge.builder(METRIC_NAME_X_WORK_WORKER_POOL_MAX_SIZE, xnioWorkerMXBean, XnioWorkerMXBean::getMaxWorkerPoolSize)
                .tags(tags)
                .tag("name", xnioWorkerMXBean.getName())
                .register(registry);
        Gauge.builder(METRIC_NAME_X_WORK_WORKER_POOL_SIZE, xnioWorkerMXBean, XnioWorkerMXBean::getWorkerPoolSize)
                .tags(tags)
                .tag("name", xnioWorkerMXBean.getName())
                .register(registry);
        Gauge.builder(METRIC_NAME_X_WORK_WORKER_THREAD_BUSY_COUNT, xnioWorkerMXBean, XnioWorkerMXBean::getBusyWorkerThreadCount)
                .tags(tags)
                .tag("name", xnioWorkerMXBean.getName())
                .register(registry);
        Gauge.builder(METRIC_NAME_X_WORK_IO_THREAD_COUNT, xnioWorkerMXBean, XnioWorkerMXBean::getIoThreadCount)
                .tags(tags)
                .tag("name", xnioWorkerMXBean.getName())
                .register(registry);
        Gauge.builder(METRIC_NAME_X_WORK_WORKER_QUEUE_SIZE, xnioWorkerMXBean, XnioWorkerMXBean::getWorkerQueueSize)
                .tags(tags)
                .tag("name", xnioWorkerMXBean.getName())
                .register(registry);
    }

    private void registerConnectorStatistics(MeterRegistry registry) {
        if (CollectionUtil.isEmpty(listenerInfoList)) {
            return;
        }
        listenerInfoList.forEach(listenerInfo -> {
            String protocol = listenerInfo.getProtcol();
            ConnectorStatistics statistics = listenerInfo.getConnectorStatistics();
            Gauge.builder(METRIC_NAME_CONNECTORS_REQUESTS_COUNT, statistics, ConnectorStatistics::getRequestCount)
                    .tags(tags)
                    .tag("protocol", protocol)
                    .register(registry);
            Gauge.builder(METRIC_NAME_CONNECTORS_REQUESTS_ERROR_COUNT, statistics, ConnectorStatistics::getErrorCount)
                    .tags(tags)
                    .tag("protocol", protocol)
                    .register(registry);
            Gauge.builder(METRIC_NAME_CONNECTORS_REQUESTS_ACTIVE, statistics, ConnectorStatistics::getActiveRequests)
                    .tags(tags)
                    .tag("protocol", protocol)
                    .baseUnit(BaseUnits.CONNECTIONS)
                    .register(registry);
            Gauge.builder(METRIC_NAME_CONNECTORS_REQUESTS_ACTIVE_MAX, statistics, ConnectorStatistics::getMaxActiveRequests)
                    .tags(tags)
                    .tag("protocol", protocol)
                    .baseUnit(BaseUnits.CONNECTIONS)
                    .register(registry);

            Gauge.builder(METRIC_NAME_CONNECTORS_BYTES_SENT, statistics, ConnectorStatistics::getBytesSent)
                    .tags(tags)
                    .tag("protocol", protocol)
                    .baseUnit(BaseUnits.BYTES)
                    .register(registry);
            Gauge.builder(METRIC_NAME_CONNECTORS_BYTES_RECEIVED, statistics, ConnectorStatistics::getBytesReceived)
                    .tags(tags)
                    .tag("protocol", protocol)
                    .baseUnit(BaseUnits.BYTES)
                    .register(registry);

            Gauge.builder(METRIC_NAME_CONNECTORS_PROCESSING_TIME, statistics, (s) -> TimeUnit.NANOSECONDS.toMillis(s.getProcessingTime()))
                    .tags(tags)
                    .tag("protocol", protocol)
                    .baseUnit(BaseUnits.MILLISECONDS)
                    .register(registry);
            Gauge.builder(METRIC_NAME_CONNECTORS_PROCESSING_TIME_MAX, statistics, (s) -> TimeUnit.NANOSECONDS.toMillis(s.getMaxProcessingTime()))
                    .tags(tags)
                    .tag("protocol", protocol)
                    .baseUnit(BaseUnits.MILLISECONDS)
                    .register(registry);

            Gauge.builder(METRIC_NAME_CONNECTORS_CONNECTIONS_ACTIVE, statistics, ConnectorStatistics::getActiveConnections)
                    .tags(tags)
                    .tag("protocol", protocol)
                    .baseUnit(BaseUnits.CONNECTIONS)
                    .register(registry);
            Gauge.builder(METRIC_NAME_CONNECTORS_CONNECTIONS_ACTIVE_MAX, statistics, ConnectorStatistics::getMaxActiveConnections)
                    .tags(tags)
                    .tag("protocol", protocol)
                    .baseUnit(BaseUnits.CONNECTIONS)
                    .register(registry);
        });

    }

    private void registerSessionStatistics(MeterRegistry registry) {
        Gauge.builder(METRIC_NAME_SESSIONS_ACTIVE_MAX, statistics, SessionManagerStatistics::getMaxActiveSessions)
                .tags(tags)
                .baseUnit(BaseUnits.SESSIONS)
                .register(registry);

        Gauge.builder(METRIC_NAME_SESSIONS_ACTIVE_CURRENT, statistics, SessionManagerStatistics::getActiveSessionCount)
                .tags(tags)
                .baseUnit(BaseUnits.SESSIONS)
                .register(registry);

        FunctionCounter.builder(METRIC_NAME_SESSIONS_CREATED, statistics, SessionManagerStatistics::getCreatedSessionCount)
                .tags(tags)
                .baseUnit(BaseUnits.SESSIONS)
                .register(registry);

        FunctionCounter.builder(METRIC_NAME_SESSIONS_EXPIRED, statistics, SessionManagerStatistics::getExpiredSessionCount)
                .tags(tags)
                .baseUnit(BaseUnits.SESSIONS)
                .register(registry);

        FunctionCounter.builder(METRIC_NAME_SESSIONS_REJECTED, statistics, SessionManagerStatistics::getRejectedSessions)
                .tags(tags)
                .baseUnit(BaseUnits.SESSIONS)
                .register(registry);

        TimeGauge.builder(METRIC_NAME_SESSIONS_ALIVE_MAX, statistics, TimeUnit.SECONDS, SessionManagerStatistics::getHighestSessionCount)
                .tags(tags)
                .register(registry);
    }

    private Undertow buildUndertow(UndertowWebServer undertowWebServer) {
        Field undertowField = ReflectionUtils.findField(UndertowWebServer.class, UNDERTOW_METRIC_NAME);
        Objects.requireNonNull(undertowField, "UndertowWebServer class field undertow not exist.");
        ReflectionUtils.makeAccessible(undertowField);
        return (Undertow) ReflectionUtils.getField(undertowField, undertowWebServer);
    }

    @Override
    public void close() {

    }

}
