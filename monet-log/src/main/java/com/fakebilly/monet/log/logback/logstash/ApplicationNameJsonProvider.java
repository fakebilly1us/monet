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
 * ApplicationNameJsonProvider
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class ApplicationNameJsonProvider extends AbstractFieldJsonProvider<ILoggingEvent> implements FieldNamesAware<LogstashFieldNames> {

    public static final String APPLICATION_NAME = "applicationName";

    @Override
    public void writeTo(JsonGenerator generator, ILoggingEvent event) throws IOException {
        String applicationName = getApplicationName(event);
        JsonWritingUtils.writeStringField(generator, getFieldName(), applicationName);
    }

    @Override
    public void setFieldNames(LogstashFieldNames fieldNames) {
        setFieldName(APPLICATION_NAME);
    }

    public String getApplicationName(ILoggingEvent event) {
        Map<String, String> map = event.getLoggerContextVO().getPropertyMap();
        return map.get(APPLICATION_NAME);
    }

}
