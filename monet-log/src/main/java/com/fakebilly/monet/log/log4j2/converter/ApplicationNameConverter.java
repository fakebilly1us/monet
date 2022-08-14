package com.fakebilly.monet.log.log4j2.converter;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.apache.logging.log4j.util.PerformanceSensitive;

/**
 * ApplicationNameConverter
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
@Plugin(name = "ApplicationNameConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({"applicationName"})
@PerformanceSensitive("allocation")
public class ApplicationNameConverter extends LogEventPatternConverter {

    private static final String NAME = "applicationName";
    private static final String STYLE = "applicationName";

    protected ApplicationNameConverter(String name, String style) {
        super(name, style);
    }

    public static ApplicationNameConverter newInstance(String[] options) {
        return new ApplicationNameConverter(NAME, STYLE);
    }

    @Override
    public void format(LogEvent event, StringBuilder toAppendTo) {

    }
}
