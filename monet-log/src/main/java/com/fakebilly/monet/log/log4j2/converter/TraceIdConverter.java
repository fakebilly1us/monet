package com.fakebilly.monet.log.log4j2.converter;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.apache.logging.log4j.util.PerformanceSensitive;

/**
 * TraceIdConverter
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Plugin(name = "TraceIdConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"traceId"})
@PerformanceSensitive("allocation")
public class TraceIdConverter extends LogEventPatternConverter {

    private static final String NAME = "traceId";
    private static final String STYLE = "traceId";

    protected TraceIdConverter(String name, String style) {
        super(name, style);
    }

    public static TraceIdConverter newInstance(String[] options) {
        return new TraceIdConverter(NAME, STYLE);
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {

    }
}
