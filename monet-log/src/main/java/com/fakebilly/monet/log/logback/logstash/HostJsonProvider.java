package com.fakebilly.monet.log.logback.logstash;

import ch.qos.logback.classic.spi.ILoggingEvent;
import com.fasterxml.jackson.core.JsonGenerator;
import net.logstash.logback.composite.AbstractFieldJsonProvider;
import net.logstash.logback.composite.FieldNamesAware;
import net.logstash.logback.composite.JsonWritingUtils;
import net.logstash.logback.fieldnames.LogstashFieldNames;

import java.io.IOException;
import java.util.Map;

/**
 * HostJsonProvider
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class HostJsonProvider extends AbstractFieldJsonProvider<ILoggingEvent> implements FieldNamesAware<LogstashFieldNames> {

    public static final String HOST = "host";

    @Override
    public void writeTo(JsonGenerator generator, ILoggingEvent event) throws IOException {
        String host = getHost(event);
        JsonWritingUtils.writeStringField(generator, getFieldName(), host);
    }

    @Override
    public void setFieldNames(LogstashFieldNames fieldNames) {
        setFieldName(HOST);
    }

    public String getHost(ILoggingEvent event) {
        Map<String, String> map = event.getLoggerContextVO().getPropertyMap();
        return map.get(HOST);
    }

}
