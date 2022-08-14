package com.fakebilly.monet.prometheus.config;

import cn.hutool.core.util.StrUtil;
import com.fakebilly.monet.prometheus.event.AfterInitEvent;
import com.fakebilly.monet.prometheus.rpc.MonetDubboMetrics;
import com.fakebilly.monet.prometheus.util.PrometheusApplicationUtil;
import com.fakebilly.monet.prometheus.web.MonetTomcatMetrics;
import com.fakebilly.monet.prometheus.web.MonetUndertowMetrics;
import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmGcMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmMemoryMetrics;
import io.micrometer.core.instrument.binder.jvm.JvmThreadMetrics;
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.binder.system.UptimeMetrics;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Manager;
import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.embedded.tomcat.TomcatWebServer;
import org.springframework.boot.web.embedded.undertow.UndertowWebServer;
import org.springframework.boot.web.server.WebServer;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.List;

/**
 * MonetMonitorConfig
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class MonetMonitorConfig {

    private static PrometheusMeterRegistry registry = null;
    private static final Object REGISTRY_LOCK = new Object();

    private static final List<Tag> TAGS = new ArrayList<>();

    private static JvmGcMetrics jvmGcMetrics;
    private static JvmMemoryMetrics jvmMemoryMetrics;
    private static JvmThreadMetrics jvmThreadMetrics;
    private static ClassLoaderMetrics classLoaderMetrics;
    private static UptimeMetrics uptimeMetrics;
    private static ProcessorMetrics processorMetrics;
    private static FileDescriptorMetrics fileDescriptorMetrics;
    private static MonetTomcatMetrics monetTomcatMetrics;
    private static MonetUndertowMetrics monetUndertowMetrics;
    private static MonetDubboMetrics monetDubboMetrics;

    public static void init(ApplicationContext applicationContext, PrometheusMeterRegistry prometheusMeterRegistry) {
        if (StrUtil.isNotBlank(MonetMonitorConfigInfo.APPLICATION_NAME)) {
            TAGS.add(new ImmutableTag(MonetMonitorConfigInfo.APPLICATION_NAME_KEY, MonetMonitorConfigInfo.APPLICATION_NAME));
        }
        registry = prometheusMeterRegistry;

        jvmGcMetrics = new JvmGcMetrics(TAGS);
        jvmGcMetrics.bindTo(registry);

        jvmMemoryMetrics = new JvmMemoryMetrics(TAGS);
        jvmMemoryMetrics.bindTo(registry);

        jvmThreadMetrics = new JvmThreadMetrics(TAGS);
        jvmThreadMetrics.bindTo(registry);

        classLoaderMetrics = new ClassLoaderMetrics(TAGS);
        classLoaderMetrics.bindTo(registry);

        uptimeMetrics = new UptimeMetrics(TAGS);
        uptimeMetrics.bindTo(registry);

        processorMetrics = new ProcessorMetrics(TAGS);
        processorMetrics.bindTo(registry);

        fileDescriptorMetrics = new FileDescriptorMetrics(TAGS);
        fileDescriptorMetrics.bindTo(registry);

        if (applicationContext instanceof WebServerApplicationContext) {
            bindWeb(applicationContext);
        }

        monetDubboMetrics = new MonetDubboMetrics(TAGS);
        monetDubboMetrics.bindTo(registry);

        PrometheusApplicationUtil.getApplicationContext().publishEvent(new AfterInitEvent(MonetMonitorConfig.class));
    }

    public static void init(PrometheusMeterRegistry prometheusMeterRegistry) {
        init(PrometheusApplicationUtil.getApplicationContext(), prometheusMeterRegistry);
    }

    public static void init(ApplicationContext applicationContext) {
        init(applicationContext, new PrometheusMeterRegistry(PrometheusConfig.DEFAULT));
    }

    public static void init() {
        init(PrometheusApplicationUtil.getApplicationContext(), new PrometheusMeterRegistry(PrometheusConfig.DEFAULT));
    }

    public static void close() {
        if (null != jvmGcMetrics) {
            jvmGcMetrics.close();
        }
        if (null != monetTomcatMetrics) {
            monetTomcatMetrics.close();
        }
        if (null != monetUndertowMetrics) {
            monetUndertowMetrics.close();
        }
        if (null != monetDubboMetrics) {
            monetDubboMetrics.close();
        }
    }

    public static PrometheusMeterRegistry getRegistry() {
        if (null == registry) {
            synchronized (REGISTRY_LOCK) {
                if (null == registry) {
                    init();
                }
            }
        }
        return registry;
    }

    private static void bindWeb(ApplicationContext applicationContext) {
        WebServer webServer = ((WebServerApplicationContext) applicationContext).getWebServer();
        if (webServer == null) {
            return;
        }
        if (webServer instanceof TomcatWebServer) {
            bindTomcat((TomcatWebServer) webServer);
        }

        if (webServer instanceof UndertowWebServer) {
            bindUndertow((UndertowWebServer) webServer);
        }
    }


    private static void bindTomcat(TomcatWebServer webServer) {
        Context context = findTomcatContext(webServer);
        if (context != null) {
            Manager manager = context.getManager();
            monetTomcatMetrics = new MonetTomcatMetrics(manager, TAGS);
            monetTomcatMetrics.bindTo(registry);
        }
    }

    private static Context findTomcatContext(TomcatWebServer tomcatWebServer) {
        Container[] containers = tomcatWebServer.getTomcat().getHost().findChildren();
        for (Container container : containers) {
            if (container instanceof Context) {
                return (Context) container;
            }
        }
        return null;
    }

    private static void bindUndertow(UndertowWebServer webServer) {
        monetUndertowMetrics = new MonetUndertowMetrics(TAGS, webServer);
        monetUndertowMetrics.bindTo(registry);
    }


}
