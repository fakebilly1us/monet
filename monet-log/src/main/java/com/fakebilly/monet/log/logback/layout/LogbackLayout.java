package com.fakebilly.monet.log.logback.layout;

import ch.qos.logback.classic.PatternLayout;
import com.fakebilly.monet.log.logback.convert.ApplicationNamePatternConverter;
import com.fakebilly.monet.log.logback.convert.HostPatternConverter;
import com.fakebilly.monet.log.logback.convert.TraceIdPatternConverter;
import com.fakebilly.monet.log.logback.mdc.LogbackMDCPatternConverter;

/**
 * LogbackLayout
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class LogbackLayout extends PatternLayout {

    static {
        DEFAULT_CONVERTER_MAP.put("traceId", TraceIdPatternConverter.class.getName());
        CONVERTER_CLASS_TO_KEY_MAP.put(TraceIdPatternConverter.class.getName(), "traceId");

        DEFAULT_CONVERTER_MAP.put("host", HostPatternConverter.class.getName());
        CONVERTER_CLASS_TO_KEY_MAP.put(HostPatternConverter.class.getName(), "host");

        DEFAULT_CONVERTER_MAP.put("applicationName", ApplicationNamePatternConverter.class.getName());
        CONVERTER_CLASS_TO_KEY_MAP.put(ApplicationNamePatternConverter.class.getName(), "applicationName");

        DEFAULT_CONVERTER_MAP.put("X", LogbackMDCPatternConverter.class.getName());
        DEFAULT_CONVERTER_MAP.put("mdc", LogbackMDCPatternConverter.class.getName());
    }
}
