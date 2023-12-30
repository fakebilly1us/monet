package com.fakebilly.monet.log.log4j2.converter;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.apache.logging.log4j.util.PerformanceSensitive;

/**
 * HostConverter
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Plugin(name = "HostConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"host"})
@PerformanceSensitive("allocation")
public class HostConverter extends LogEventPatternConverter {

    private static final String NAME = "host";
    private static final String STYLE = "host";

    protected HostConverter(String name, String style) {
        super(name, style);
    }

    public static HostConverter newInstance(String[] options) {
        return new HostConverter(NAME, STYLE);
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {

    }
}
