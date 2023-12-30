package com.fakebilly.monet.prometheus.web;

import io.micrometer.core.instrument.*;
import io.micrometer.core.instrument.binder.BaseUnits;
import io.micrometer.core.instrument.binder.MeterBinder;
import io.micrometer.core.lang.Nullable;
import org.apache.catalina.Manager;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * MonetUndertowMetrics
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class MonetTomcatMetrics implements MeterBinder, AutoCloseable {

    private static final String JMX_DOMAIN_EMBEDDED = "Tomcat";
    private static final String JMX_DOMAIN_STANDALONE = "Catalina";
    private static final String OBJECT_NAME_SERVER_SUFFIX = ":type=Server";
    private static final String OBJECT_NAME_SERVER_EMBEDDED = JMX_DOMAIN_EMBEDDED + OBJECT_NAME_SERVER_SUFFIX;
    private static final String OBJECT_NAME_SERVER_STANDALONE = JMX_DOMAIN_STANDALONE + OBJECT_NAME_SERVER_SUFFIX;

    private static final String METRIC_NAME_TOMCAT_SESSIONS_ACTIVE_MAX = "tomcat.sessions.active.max";
    private static final String METRIC_NAME_TOMCAT_SESSIONS_ACTIVE_CURRENT = "tomcat.sessions.active.current";
    private static final String METRIC_NAME_TOMCAT_SESSIONS_CREATED = "tomcat.sessions.created";
    private static final String METRIC_NAME_TOMCAT_SESSIONS_EXPIRED = "tomcat.sessions.expired";
    private static final String METRIC_NAME_TOMCAT_SESSIONS_REJECTED = "tomcat.sessions.rejected";
    private static final String METRIC_NAME_TOMCAT_SESSIONS_ALIVE_MAX = "tomcat.sessions.alive.max";

    private static final String METRIC_NAME_TOMCAT_THREADS_CONFIG_MAX = "tomcat.threads.config.max";
    private static final String METRIC_NAME_TOMCAT_THREADS_BUSY = "tomcat.threads.busy";
    private static final String METRIC_NAME_TOMCAT_THREADS_CURRENT = "tomcat.threads.current";
    private static final String METRIC_NAME_TOMCAT_CONNECTIONS_CURRENT = "tomcat.connections.current";
    private static final String METRIC_NAME_TOMCAT_CONNECTIONS_KEEPALIVE_CURRENT = "tomcat.connections.keepalive.current";
    private static final String METRIC_NAME_TOMCAT_CONNECTIONS_CONFIG_MAX = "tomcat.connections.config.max";

    private static final String METRIC_NAME_TOMCAT_CACHE_ACCESS = "tomcat.cache.access";
    private static final String METRIC_NAME_TOMCAT_CACHE_HIT = "tomcat.cache.hit";

    private static final String METRIC_NAME_TOMCAT_SERVLET_ERROR = "tomcat.servlet.error";
    private static final String METRIC_NAME_TOMCAT_SERVLET_REQUEST = "tomcat.servlet.request";
    private static final String METRIC_NAME_TOMCAT_SERVLET_REQUEST_MAX = "tomcat.servlet.request.max";

    private static final String METRIC_NAME_TOMCAT_GLOBAL_SENT = "tomcat.global.sent";
    private static final String METRIC_NAME_TOMCAT_GLOBAL_RECEIVED = "tomcat.global.received";
    private static final String METRIC_NAME_TOMCAT_GLOBAL_ERROR = "tomcat.global.error";
    private static final String METRIC_NAME_TOMCAT_GLOBAL_REQUEST = "tomcat.global.request";
    private static final String METRIC_NAME_TOMCAT_GLOBAL_REQUEST_MAX = "tomcat.global.request.max";

    @Nullable
    private final Manager manager;

    private final MBeanServer mBeanServer;
    private final Iterable<Tag> tags;

    private final Set<NotificationListener> notificationListeners = ConcurrentHashMap.newKeySet();

    private volatile String jmxDomain;

    public MonetTomcatMetrics(@Nullable Manager manager, Iterable<Tag> tags) {
        this(manager, tags, getMBeanServer());
    }

    public MonetTomcatMetrics(Iterable<Tag> tags) {
        this(null, tags, getMBeanServer());
    }

    public MonetTomcatMetrics(@Nullable Manager manager, Iterable<Tag> tags, MBeanServer mBeanServer) {
        this.manager = manager;
        this.tags = tags;
        this.mBeanServer = mBeanServer;

        if (manager != null) {
            this.jmxDomain = manager.getContext().getDomain();
        }
    }

    public static MBeanServer getMBeanServer() {
        List<MBeanServer> mBeanServers = MBeanServerFactory.findMBeanServer(null);
        if (!mBeanServers.isEmpty()) {
            return mBeanServers.get(0);
        }
        return ManagementFactory.getPlatformMBeanServer();
    }

    @Override
    public void bindTo(MeterRegistry registry) {
        registerGlobalRequestMetrics(registry);
        registerServletMetrics(registry);
        registerCacheMetrics(registry);
        registerThreadPoolMetrics(registry);
        registerSessionMetrics(registry);
    }

    private void registerSessionMetrics(MeterRegistry registry) {
        if (manager == null) {
            // If the binder is created but unable to find the session manager don't register those metrics
            return;
        }
        Gauge.builder(METRIC_NAME_TOMCAT_SESSIONS_ACTIVE_MAX, manager, Manager::getMaxActive)
                .tags(tags)
                .baseUnit(BaseUnits.SESSIONS)
                .register(registry);

        Gauge.builder(METRIC_NAME_TOMCAT_SESSIONS_ACTIVE_CURRENT, manager, Manager::getActiveSessions)
                .tags(tags)
                .baseUnit(BaseUnits.SESSIONS)
                .register(registry);

        FunctionCounter.builder(METRIC_NAME_TOMCAT_SESSIONS_CREATED, manager, Manager::getSessionCounter)
                .tags(tags)
                .baseUnit(BaseUnits.SESSIONS)
                .register(registry);

        FunctionCounter.builder(METRIC_NAME_TOMCAT_SESSIONS_EXPIRED, manager, Manager::getExpiredSessions)
                .tags(tags)
                .baseUnit(BaseUnits.SESSIONS)
                .register(registry);

        FunctionCounter.builder(METRIC_NAME_TOMCAT_SESSIONS_REJECTED, manager, Manager::getRejectedSessions)
                .tags(tags)
                .baseUnit(BaseUnits.SESSIONS)
                .register(registry);

        TimeGauge.builder(METRIC_NAME_TOMCAT_SESSIONS_ALIVE_MAX, manager, TimeUnit.SECONDS, Manager::getSessionMaxAliveTime)
                .tags(tags)
                .register(registry);
    }

    private void registerThreadPoolMetrics(MeterRegistry registry) {
        registerMetricsEventually(":type=ThreadPool,name=*", (name, allTags) -> {
            Gauge.builder(METRIC_NAME_TOMCAT_THREADS_CONFIG_MAX, mBeanServer,
                            s -> safeDouble(() -> s.getAttribute(name, "maxThreads")))
                    .tags(allTags)
                    .baseUnit(BaseUnits.THREADS)
                    .register(registry);

            Gauge.builder(METRIC_NAME_TOMCAT_THREADS_BUSY, mBeanServer,
                            s -> safeDouble(() -> s.getAttribute(name, "currentThreadsBusy")))
                    .tags(allTags)
                    .baseUnit(BaseUnits.THREADS)
                    .register(registry);

            Gauge.builder(METRIC_NAME_TOMCAT_THREADS_CURRENT, mBeanServer,
                            s -> safeDouble(() -> s.getAttribute(name, "currentThreadCount")))
                    .tags(allTags)
                    .baseUnit(BaseUnits.THREADS)
                    .register(registry);
            Gauge.builder(METRIC_NAME_TOMCAT_CONNECTIONS_CURRENT, this.mBeanServer, (s) -> {
                return this.safeDouble(() -> {
                    return s.getAttribute(name, "connectionCount");
                });
            }).tags(allTags).baseUnit("connections").register(registry);
            Gauge.builder(METRIC_NAME_TOMCAT_CONNECTIONS_KEEPALIVE_CURRENT, this.mBeanServer, (s) -> {
                return this.safeDouble(() -> {
                    return s.getAttribute(name, "keepAliveCount");
                });
            }).tags(allTags).baseUnit("connections").register(registry);
            Gauge.builder(METRIC_NAME_TOMCAT_CONNECTIONS_CONFIG_MAX, this.mBeanServer, (s) -> {
                return this.safeDouble(() -> {
                    return s.getAttribute(name, "maxConnections");
                });
            }).tags(allTags).baseUnit("connections").register(registry);
        });
    }

    private void registerCacheMetrics(MeterRegistry registry) {
        registerMetricsEventually(":type=StringCache", (name, allTags) -> {
            FunctionCounter.builder(METRIC_NAME_TOMCAT_CACHE_ACCESS, mBeanServer,
                            s -> safeDouble(() -> s.getAttribute(name, "accessCount")))
                    .tags(allTags)
                    .register(registry);

            FunctionCounter.builder(METRIC_NAME_TOMCAT_CACHE_HIT, mBeanServer,
                            s -> safeDouble(() -> s.getAttribute(name, "hitCount")))
                    .tags(allTags)
                    .register(registry);
        });
    }

    private void registerServletMetrics(MeterRegistry registry) {
        registerMetricsEventually(":j2eeType=Servlet,name=*,*", (name, allTags) -> {
            FunctionCounter.builder(METRIC_NAME_TOMCAT_SERVLET_ERROR, mBeanServer,
                            s -> safeDouble(() -> s.getAttribute(name, "errorCount")))
                    .tags(allTags)
                    .register(registry);

            FunctionTimer.builder(METRIC_NAME_TOMCAT_SERVLET_REQUEST, mBeanServer,
                            s -> safeLong(() -> s.getAttribute(name, "requestCount")),
                            s -> safeDouble(() -> s.getAttribute(name, "processingTime")), TimeUnit.MILLISECONDS)
                    .tags(allTags)
                    .register(registry);

            TimeGauge.builder(METRIC_NAME_TOMCAT_SERVLET_REQUEST_MAX, mBeanServer, TimeUnit.MILLISECONDS,
                            s -> safeDouble(() -> s.getAttribute(name, "maxTime")))
                    .tags(allTags)
                    .register(registry);
        });
    }

    private void registerGlobalRequestMetrics(MeterRegistry registry) {
        registerMetricsEventually(":type=GlobalRequestProcessor,name=*", (name, allTags) -> {
            FunctionCounter.builder(METRIC_NAME_TOMCAT_GLOBAL_SENT, mBeanServer,
                            s -> safeDouble(() -> s.getAttribute(name, "bytesSent")))
                    .tags(allTags)
                    .baseUnit(BaseUnits.BYTES)
                    .register(registry);

            FunctionCounter.builder(METRIC_NAME_TOMCAT_GLOBAL_RECEIVED, mBeanServer,
                            s -> safeDouble(() -> s.getAttribute(name, "bytesReceived")))
                    .tags(allTags)
                    .baseUnit(BaseUnits.BYTES)
                    .register(registry);

            FunctionCounter.builder(METRIC_NAME_TOMCAT_GLOBAL_ERROR, mBeanServer,
                            s -> safeDouble(() -> s.getAttribute(name, "errorCount")))
                    .tags(allTags)
                    .register(registry);

            FunctionTimer.builder(METRIC_NAME_TOMCAT_GLOBAL_REQUEST, mBeanServer,
                            s -> safeLong(() -> s.getAttribute(name, "requestCount")),
                            s -> safeDouble(() -> s.getAttribute(name, "processingTime")), TimeUnit.MILLISECONDS)
                    .tags(allTags)
                    .register(registry);

            TimeGauge.builder(METRIC_NAME_TOMCAT_GLOBAL_REQUEST_MAX, mBeanServer, TimeUnit.MILLISECONDS,
                            s -> safeDouble(() -> s.getAttribute(name, "maxTime")))
                    .tags(allTags)
                    .register(registry);
        });
    }

    /**
     * If the Tomcat MBeans already exist, register metrics immediately. Otherwise register an MBean registration listener
     * with the MBeanServer and register metrics when/if the MBeans becomes available.
     */
    private void registerMetricsEventually(String namePatternSuffix, BiConsumer<ObjectName, Iterable<Tag>> perObject) {
        if (getJmxDomain() != null) {
            Set<ObjectName> objectNames = this.mBeanServer.queryNames(getNamePattern(namePatternSuffix), null);
            if (!objectNames.isEmpty()) {
                // MBeans are present, so we can register metrics now.
                objectNames.forEach(objectName -> perObject.accept(objectName, Tags.concat(tags, nameTag(objectName))));
                return;
            }
        }

        // MBean isn't yet registered, so we'll set up a notification to wait for them to be present and register
        // metrics later.
        NotificationListener notificationListener = new NotificationListener() {
            @Override
            public void handleNotification(Notification notification, Object handback) {
                MBeanServerNotification mBeanServerNotification = (MBeanServerNotification) notification;
                ObjectName objectName = mBeanServerNotification.getMBeanName();
                perObject.accept(objectName, Tags.concat(tags, nameTag(objectName)));
                if (getNamePattern(namePatternSuffix).isPattern()) {
                    // patterns can match multiple MBeans so don't remove listener
                    return;
                }
                try {
                    mBeanServer.removeNotificationListener(MBeanServerDelegate.DELEGATE_NAME, this);
                    notificationListeners.remove(this);
                } catch (InstanceNotFoundException | ListenerNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            }
        };
        notificationListeners.add(notificationListener);

        NotificationFilter notificationFilter = (NotificationFilter) notification -> {
            if (!MBeanServerNotification.REGISTRATION_NOTIFICATION.equals(notification.getType())) {
                return false;
            }

            // we can safely downcast now
            ObjectName objectName = ((MBeanServerNotification) notification).getMBeanName();
            return getNamePattern(namePatternSuffix).apply(objectName);
        };

        try {
            mBeanServer.addNotificationListener(MBeanServerDelegate.DELEGATE_NAME, notificationListener, notificationFilter, null);
        } catch (InstanceNotFoundException e) {
            // should never happen
            throw new RuntimeException("Error registering MBean listener", e);
        }
    }

    private ObjectName getNamePattern(String namePatternSuffix) {
        try {
            return new ObjectName(getJmxDomain() + namePatternSuffix);
        } catch (MalformedObjectNameException e) {
            // should never happen
            throw new RuntimeException("Error registering Tomcat JMX based metrics", e);
        }
    }

    private String getJmxDomain() {
        if (this.jmxDomain == null) {
            if (hasObjectName(OBJECT_NAME_SERVER_EMBEDDED)) {
                this.jmxDomain = JMX_DOMAIN_EMBEDDED;
            } else if (hasObjectName(OBJECT_NAME_SERVER_STANDALONE)) {
                this.jmxDomain = JMX_DOMAIN_STANDALONE;
            }
        }
        return this.jmxDomain;
    }

    /**
     * Set JMX domain. If unset, default values will be used as follows:
     *
     * <ul>
     *     <li>Embedded Tomcat: "Tomcat"</li>
     *     <li>Standalone Tomcat: "Catalina"</li>
     * </ul>
     *
     * @param jmxDomain JMX domain to be used
     * @since 1.0.11
     */
    public void setJmxDomain(String jmxDomain) {
        this.jmxDomain = jmxDomain;
    }

    private boolean hasObjectName(String name) {
        try {
            return this.mBeanServer.queryNames(new ObjectName(name), null).size() == 1;
        } catch (MalformedObjectNameException ex) {
            throw new RuntimeException(ex);
        }
    }

    private double safeDouble(Callable<Object> callable) {
        try {
            return Double.parseDouble(callable.call().toString());
        } catch (Exception e) {
            return Double.NaN;
        }
    }

    private long safeLong(Callable<Object> callable) {
        try {
            return Long.parseLong(callable.call().toString());
        } catch (Exception e) {
            return 0;
        }
    }

    private Iterable<Tag> nameTag(ObjectName name) {
        String nameTagValue = name.getKeyProperty("name");
        if (nameTagValue != null) {
            return Tags.of("name", nameTagValue.replaceAll("\"", ""));
        }
        return Collections.emptyList();
    }

    @Override
    public void close() {
        for (NotificationListener notificationListener : this.notificationListeners) {
            try {
                this.mBeanServer.removeNotificationListener(MBeanServerDelegate.DELEGATE_NAME, notificationListener);
            } catch (InstanceNotFoundException | ListenerNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
