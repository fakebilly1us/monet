package com.fakebilly.monet.log.logback.convert;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * LogbackHostPatternConverter
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class HostPatternConverter extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        return "";
    }
}
