package com.fakebilly.monet.log.log4j2.mdc;

import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.core.pattern.PatternConverter;
import org.apache.logging.log4j.util.PerformanceSensitive;
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.util.TriConsumer;
import org.apache.logging.log4j.util.StringBuilders;

/**
 * Log4j2MdcPatternConverter
 * Able to handle the contents of the LogEvent's MDC and either
 * output the entire contents of the properties in a similar format to the
 * java.util.Hashtable.toString(), or to output the value of a specific key
 * within the property bundle
 * when this pattern converter has the option set.
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Plugin(name = "Log4j2MdcPatternConverter", category = PatternConverter.CATEGORY)
@ConverterKeys({ "X", "mdc", "MDC" })
@PerformanceSensitive("allocation")
public final class Log4j2MdcPatternConverter extends LogEventPatternConverter {

    /**
     * Name of property to output.
     */
    private final String key;
    private final String[] keys;
    private final boolean full;

    private final static String KEY_HOST = "host";
    private final static String KEY_APPLICATION_NAME = "applicationName";
    private final static String KEY_TRACE_ID = "traceId";

    /**
     * Private constructor.
     *
     * @param options options, may be null.
     */
    private Log4j2MdcPatternConverter(final String[] options) {
        super(options != null && options.length > 0 ? "MDC{" + options[0] + '}' : "MDC", "mdc");
        if (options != null && options.length > 0) {
            full = false;
            if (options[0].indexOf(',') > 0) {
                keys = options[0].split(",");
                for (int i = 0; i < keys.length; i++) {
                    keys[i] = keys[i].trim();
                }
                key = null;
            } else {
                keys = null;
                key = options[0];
            }
        } else {
            full = true;
            key = null;
            keys = null;
        }
    }

    /**
     * Obtains an instance of PropertiesPatternConverter.
     *
     * @param options options, may be null or first element contains name of property to format.
     * @return instance of PropertiesPatternConverter.
     */
    public static Log4j2MdcPatternConverter newInstance(final String[] options) {
        return new Log4j2MdcPatternConverter(options);
    }

    private static final TriConsumer<String, Object, StringBuilder> WRITE_KEY_VALUES_INTO = (key, value, sb) -> {
        sb.append(key).append('=');
        StringBuilders.appendValue(sb, value);
        sb.append(", ");
    };

    /**
     * {@inheritDoc}
     */
    @Override
    public void format(final LogEvent event, final StringBuilder toAppendTo) {
        if (null != key && KEY_HOST.equals(key)) {
            convertHost(event, toAppendTo);
            return;
        } else if (null != key && KEY_APPLICATION_NAME.equals(key)) {
            convertApplicationName(event, toAppendTo);
            return;
        } else if (null != key && KEY_TRACE_ID.equals(key)) {
            convertTraceId(event, toAppendTo);
            return;
        }
        // default
        defaultFormat(event, toAppendTo);
    }

    private void defaultFormat(final LogEvent event, final StringBuilder toAppendTo) {
        final ReadOnlyStringMap contextData = event.getContextData();
        // if there is no additional options, we output every single
        // Key/Value pair for the MDC in a similar format to Hashtable.toString()
        if (full) {
            if (contextData == null || contextData.isEmpty()) {
                toAppendTo.append("{}");
                return;
            }
            appendFully(contextData, toAppendTo);
        } else if (keys != null) {
            if (contextData == null || contextData.isEmpty()) {
                toAppendTo.append("{}");
                return;
            }
            appendSelectedKeys(keys, contextData, toAppendTo);
        } else if (contextData != null){
            // otherwise they just want a single key output
            final Object value = contextData.getValue(key);
            if (value != null) {
                StringBuilders.appendValue(toAppendTo, value);
            }
        }
    }

    private static void appendFully(final ReadOnlyStringMap contextData, final StringBuilder toAppendTo) {
        toAppendTo.append("{");
        final int start = toAppendTo.length();
        contextData.forEach(WRITE_KEY_VALUES_INTO, toAppendTo);
        final int end = toAppendTo.length();
        if (end > start) {
            toAppendTo.setCharAt(end - 2, '}');
            toAppendTo.deleteCharAt(end - 1);
        } else {
            toAppendTo.append('}');
        }
    }

    private static void appendSelectedKeys(final String[] keys, final ReadOnlyStringMap contextData, final StringBuilder sb) {
        // Print all the keys in the array that have a value.
        final int start = sb.length();
        sb.append('{');
        for (int i = 0; i < keys.length; i++) {
            final String theKey = keys[i];
            final Object value = contextData.getValue(theKey);
            if (value != null) {
                // !contextData.containskey(theKey)
                if (sb.length() - start > 1) {
                    sb.append(", ");
                }
                sb.append(theKey).append('=');
                StringBuilders.appendValue(sb, value);
            }
        }
        sb.append('}');
    }

    public void convertTraceId(LogEvent event, StringBuilder toAppendTo) {

    }

    public void convertHost(LogEvent event, StringBuilder toAppendTo) {

    }

    public void convertApplicationName(LogEvent event, StringBuilder toAppendTo) {

    }
}
