package com.fakebilly.monet.prometheus.monitor;

import cn.hutool.core.util.StrUtil;
import com.fakebilly.monet.prometheus.config.MonetMonitorConfig;
import com.fakebilly.monet.prometheus.config.MonetMonitorConfigInfo;
import com.fakebilly.monet.prometheus.gauge.MonetGaugeMetrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * MonetMonitor
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class MonetMonitor {

    private static final Logger logger = LoggerFactory.getLogger(MonetMonitor.class);

    private static final Map<String, Counter> COUNTER_MAP = new ConcurrentHashMap<>(1024);
    private static final Map<String, Timer> TIMER_MAP = new ConcurrentHashMap<>(1024);
    private static final Map<String, Gauge> GAUGE_MAP = new ConcurrentHashMap<>(1024);

    private static final Object COUNTER_LOCK = new Object();
    private static final Object TIMER_LOCK = new Object();
    private static final Object GAUGE_LOCK = new Object();

    private static final int DEFAULT_RECORD_VALUE = 1;
    private static final String[] DEFAULT_RECORD_TAGS = null;
    private static final double[] DEFAULT_PUBLISH_PERCENTILES = new double[]{0.95};

    private static final String CUSTOMIZE_METHOD_KEY = "customize_method";
    private static final String CUSTOMIZE_METHOD_TAG_KEY = "method";

    public static void recordCounter(String methodName) {
        recordCounterOne(CUSTOMIZE_METHOD_KEY, CUSTOMIZE_METHOD_TAG_KEY, methodName);
    }

    public static void recordCounterOne(String key) {
        recordCounterWithTags(key, DEFAULT_RECORD_VALUE, DEFAULT_RECORD_TAGS);
    }

    public static void recordCounterOne(String key, String tagKey, String tagValue) {
        recordCounterWithTags(key, DEFAULT_RECORD_VALUE, tagKey, tagValue);
    }

    public static void recordCounterWithTags(String key, int value, String... tags) {
        if (MonetMonitorConfig.getRegistry() != null) {
            if (value <= 0) {
                logger.warn("record count must not be negative, key:{}, value:{}", key, value);
            } else {
                try {
                    getCounter(key, tags).increment(value);
                } catch (Exception e) {
                    logger.warn("record count key:{}, e:{}", key, e);
                }
            }
        }
    }

    public static void recordTime(String methodName, long time) {
        recordTime(CUSTOMIZE_METHOD_KEY, time, CUSTOMIZE_METHOD_TAG_KEY, methodName);
    }

    public static void recordTime(String methodName, long time, TimeUnit timeUnit) {
        recordTimeWithTags(CUSTOMIZE_METHOD_KEY, time, timeUnit, CUSTOMIZE_METHOD_TAG_KEY, methodName);
    }

    public static void recordTimeMill(String key, long time) {
        recordTimeWithTags(key, time, TimeUnit.MILLISECONDS, DEFAULT_RECORD_TAGS);
    }

    public static void recordTime(String key, long time, String tagKey, String tagValue) {
        recordTimeWithTags(key, time, TimeUnit.MILLISECONDS, tagKey, tagValue);
    }

    public static void recordTimeWithTags(String key, long time, TimeUnit timeUnit, String... tags) {
        if (MonetMonitorConfig.getRegistry() != null) {
            if (time < 0L) {
                logger.warn("record time must not be negative, key: {}, time: {}", key, time);
            } else {
                try {
                    getTimer(key, tags).record(time, timeUnit);
                } catch (Exception e) {
                    logger.warn("record time key: {}, e: {}", key, e);
                }

            }
        }
    }

    private static Counter getCounter(String metricsName, String... tags) {
        String entryKey = getEntryKey(metricsName, tags);
        Counter counter = COUNTER_MAP.get(entryKey);
        if (counter == null) {
            synchronized (COUNTER_LOCK) {
                counter = COUNTER_MAP.get(entryKey);
                if (counter != null) {
                    return counter;
                }
                if (StrUtil.isNotBlank(MonetMonitorConfigInfo.APPLICATION_NAME)) {
                    counter = Counter.builder(metricsName).tag(MonetMonitorConfigInfo.APPLICATION_NAME_KEY, MonetMonitorConfigInfo.APPLICATION_NAME).tags(tags).register(MonetMonitorConfig.getRegistry());
                } else {
                    counter = Counter.builder(metricsName).tags(tags).register(MonetMonitorConfig.getRegistry());
                }
                COUNTER_MAP.put(entryKey, counter);
            }
        }
        return counter;
    }

    private static Timer getTimer(String metricsName, String... tags) {
        String entryKey = getEntryKey(metricsName, tags);
        Timer timer = TIMER_MAP.get(entryKey);
        if (timer == null) {
            synchronized (TIMER_LOCK) {
                timer = TIMER_MAP.get(entryKey);
                if (timer != null) {
                    return timer;
                }
                if (StrUtil.isNotBlank(MonetMonitorConfigInfo.APPLICATION_NAME)) {
                    timer = Timer.builder(metricsName).tag(MonetMonitorConfigInfo.APPLICATION_NAME_KEY, MonetMonitorConfigInfo.APPLICATION_NAME).tags(tags).publishPercentiles(DEFAULT_PUBLISH_PERCENTILES).register(MonetMonitorConfig.getRegistry());
                } else {
                    timer = Timer.builder(metricsName).tags(tags).publishPercentiles(DEFAULT_PUBLISH_PERCENTILES).register(MonetMonitorConfig.getRegistry());
                }
                TIMER_MAP.put(entryKey, timer);
            }
        }
        return timer;
    }

    private static Gauge getGauge(String metricsName, MonetGaugeMetrics metrics, String... tags) {
        String entryKey = getEntryKey(metricsName, tags);
        Gauge gauge = GAUGE_MAP.get(entryKey);
        if (gauge == null) {
            synchronized (GAUGE_LOCK) {
                gauge = GAUGE_MAP.get(entryKey);
                if (gauge != null) {
                    return gauge;
                }
                if (StrUtil.isNotBlank(MonetMonitorConfigInfo.APPLICATION_NAME)) {
                    gauge = Gauge.builder(metricsName, metrics, MonetGaugeMetrics::doubleValue).tag(MonetMonitorConfigInfo.APPLICATION_NAME_KEY, MonetMonitorConfigInfo.APPLICATION_NAME).tags(tags).register(MonetMonitorConfig.getRegistry());
                } else {
                    gauge = Gauge.builder(metricsName, metrics, MonetGaugeMetrics::doubleValue).tags(tags).register(MonetMonitorConfig.getRegistry());
                }
                GAUGE_MAP.put(entryKey, gauge);
            }
        }
        return gauge;
    }

    private static String getEntryKey(String metricsName, String... tags) {
        if (tags != null && tags.length > 0) {
            StringBuilder sb = new StringBuilder(metricsName);
            for (String tag : tags) {
                sb.append(tag);
            }
            return sb.toString();
        } else {
            return metricsName;
        }
    }
}
