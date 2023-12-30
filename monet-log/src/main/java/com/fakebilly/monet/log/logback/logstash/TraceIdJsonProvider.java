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
 * TraceIdJsonProvider
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class TraceIdJsonProvider extends AbstractFieldJsonProvider<ILoggingEvent> implements FieldNamesAware<LogstashFieldNames> {

    public static final String TRACING_ID = "traceId";

    @Override
    public void writeTo(JsonGenerator generator, ILoggingEvent event) throws IOException {
        String tracingId = getTracingId(event);
        JsonWritingUtils.writeStringField(generator, getFieldName(), tracingId);
    }

    @Override
    public void setFieldNames(LogstashFieldNames fieldNames) {
        setFieldName(TRACING_ID);
    }

    public String getTracingId(ILoggingEvent event) {
        Map<String, String> map = event.getLoggerContextVO().getPropertyMap();
        return map.get(TRACING_ID);
    }

}
