package com.fakebilly.monet.log.config;

import com.fakebilly.monet.log.ILogger;
import com.fakebilly.monet.log.log4j2.config.Log4j2Config;
import com.fakebilly.monet.log.logback.config.LogbackConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * LogUtil
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public abstract class LogConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(LogConfig.class);

    private static volatile boolean started = false;

    public static final String EXT_PATTERN = "[%X{host}] [%X{applicationName}] [%X{traceId}] [%X{keywordFirst}] [%X{keywordSecond}] [%X{keywordThird}] ";

    public static String EXT_LOGGER_CLASS_NAME = ILogger.class.getName();

    public static void initPattern() {
        if (!started) {
            synchronized (LogConfig.class) {
                if (!started) {
                    try {
                        // log4j2
                        Class.forName("org.apache.logging.slf4j.Log4jLogger");
                        Log4j2Config.initPattern();
                        started = true;
                        LOGGER.info("Log with log4j2");
                        return;
                    } catch (ClassNotFoundException e) {
                    }
                    try {
                        // logback
                        Class.forName("ch.qos.logback.classic.Logger");
                        LogbackConfig.initPattern();
                        started = true;
                        LOGGER.info("Log with logback");
                        return;
                    } catch (ClassNotFoundException e) {
                    }
                }
            }
        }
    }

}
