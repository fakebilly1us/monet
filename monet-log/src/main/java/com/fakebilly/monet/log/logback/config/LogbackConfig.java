package com.fakebilly.monet.log.logback.config;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Appender;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import ch.qos.logback.core.OutputStreamAppender;
import ch.qos.logback.core.encoder.Encoder;
import com.fakebilly.monet.log.config.LogConfig;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;

/**
 * LogbackConfig
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class LogbackConfig {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(LogbackConfig.class);

    public static final String FIELD_ENCODER = "encoder";

    public static void initPattern() {
        // 处理 PatternLayout pattern
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        List<Logger> loggers = loggerContext.getLoggerList();
        Iterator<Appender<ILoggingEvent>> appenders = null;
        Appender<ILoggingEvent> appender = null;
        Encoder<ILoggingEvent> encoder = null;
        PatternLayoutEncoder patternLayoutEncoder = null;
        for (Logger logger : loggers) {
            appenders = logger.iteratorForAppenders();
            while (appenders.hasNext()) {
                appender = appenders.next();
                if (appender instanceof FileAppender || appender instanceof ConsoleAppender) {
                    // 处理 FileAppender ConsoleAppender
                    OutputStreamAppender<ILoggingEvent> outputStreamAppender = (OutputStreamAppender<ILoggingEvent>) appender;
                    encoder = outputStreamAppender.getEncoder();
                    if (encoder instanceof PatternLayoutEncoder) {
                        patternLayoutEncoder = (PatternLayoutEncoder) encoder;
                        modifyPatternLayout(patternLayoutEncoder, outputStreamAppender);
                    }
                }
                appender.start();
            }
        }
    }

    public static void modifyPatternLayout(PatternLayoutEncoder patternLayoutEncoder, OutputStreamAppender<ILoggingEvent> outputStreamAppender) {
        try {
            patternLayoutEncoder.setPattern(LogConfig.EXT_PATTERN + patternLayoutEncoder.getPattern());
            patternLayoutEncoder.setCharset(StandardCharsets.UTF_8);
            patternLayoutEncoder.setImmediateFlush(true);
            Field encoderField = OutputStreamAppender.class.getDeclaredField(FIELD_ENCODER);
            encoderField.setAccessible(true);
            encoderField.set(outputStreamAppender, patternLayoutEncoder);
            patternLayoutEncoder.start();
            outputStreamAppender.start();
        } catch (Exception e) {
            LOGGER.warn("LogbackConfig modifyPatternLayout Exception ", e);
        }
    }

}
