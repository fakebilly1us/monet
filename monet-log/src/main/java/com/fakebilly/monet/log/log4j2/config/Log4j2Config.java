package com.fakebilly.monet.log.log4j2.config;

import com.fakebilly.monet.log.config.LogConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Log4j2Config
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class Log4j2Config {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Log4j2Config.class);

    private static final String FIELD_LAYOUT = "layout";

    @SuppressWarnings("rawtypes")
    public static void initPattern() {
        // 处理 PatternLayout pattern
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        if (null != ctx) {
            Configuration config = ctx.getConfiguration();
            if (null != config) {
                Map<String, Appender> appenderMap = config.getAppenders();
                if (null != appenderMap && appenderMap.size() > 0) {
                    Layout layout = null;
                    for (Entry<String, Appender> appenderEntry : appenderMap.entrySet()) {
                        layout = appenderEntry.getValue().getLayout();
                        if (null != layout && layout instanceof PatternLayout) {
                            modifyPatternLayout(appenderEntry.getValue(), (PatternLayout) layout);
                        }
                    }
                }
            }
        }
    }

    public static void modifyPatternLayout(Appender appender, PatternLayout layout) {
        try {
            StringBuilder pattern = new StringBuilder(LogConfig.EXT_PATTERN)
                    .append(layout.getConversionPattern());
            PatternLayout patternLayout = PatternLayout.newBuilder().withConfiguration(layout.getConfiguration())
                    .withPattern(pattern.toString()).build();
            Field field = AbstractAppender.class.getDeclaredField(FIELD_LAYOUT);
            field.setAccessible(true);
            field.set(appender, patternLayout);
        } catch (Exception e) {
            LOGGER.warn("Log4j2Config modifyPatternLayout Exception ", e);
        }
    }

}
