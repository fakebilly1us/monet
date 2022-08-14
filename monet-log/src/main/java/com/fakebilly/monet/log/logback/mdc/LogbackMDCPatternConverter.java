package com.fakebilly.monet.log.logback.mdc;

import ch.qos.logback.classic.pattern.MDCConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.util.OptionHelper;

/**
 * LogbackMDCPatternConverter
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class LogbackMDCPatternConverter extends MDCConverter {

    private static final String CONVERT_TRACE_ID_KEY = "traceId";
    private static final String CONVERT_HOST_KEY = "host";
    private static final String CONVERT_APPLICATION_NAME_KEY = "applicationName";

    private boolean convert4TraceId = false;
    private boolean convert4Host = false;
    private boolean convert4ApplicationName = false;

    @Override
    public void start() {
        super.start();
        String[] key = OptionHelper.extractDefaultReplacement(getFirstOption());
        if (null != key && key.length > 0) {
            String variableName = key[0];
            if (CONVERT_TRACE_ID_KEY.equals(variableName)) {
                convert4TraceId = true;
            } else if (CONVERT_HOST_KEY.equals(variableName)) {
                convert4Host = true;
            } else if (CONVERT_APPLICATION_NAME_KEY.equals(variableName)) {
                convert4ApplicationName = true;
            }
        }
    }

    @Override
    public String convert(ILoggingEvent iLoggingEvent) {
        if (convert4TraceId) {
            return convertTraceId(iLoggingEvent);
        } else if (convert4Host) {
            return convertHost(iLoggingEvent);
        } else if (convert4ApplicationName) {
            return convertApplicationName(iLoggingEvent);
        }
        return super.convert(iLoggingEvent);
    }

    public String convertTraceId(ILoggingEvent event) {
        return "";
    }

    public String convertHost(ILoggingEvent event) {
        return "";
    }

    public String convertApplicationName(ILoggingEvent event) {
        return "";
    }
}
