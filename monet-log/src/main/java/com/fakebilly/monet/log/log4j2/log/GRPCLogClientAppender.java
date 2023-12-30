package com.fakebilly.monet.log.log4j2.log;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractOutputStreamAppender;
import org.apache.logging.log4j.core.appender.OutputStreamManager;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.Property;
import org.apache.logging.log4j.core.config.plugins.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * GRPCLogClientAppender
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
@Plugin(name = "GRPCLogClientAppender", category = "Core", elementType = "appender")
public class GRPCLogClientAppender extends AbstractOutputStreamAppender<OutputStreamManager> {

    private static final String NAME = "Discard";
    private static final String STREAM_NAME = "Discard";

    private static final OutputStream DISCARDED_STREAM = new OutputStream() {
        @Override
        public void write(final int b) throws IOException {
            // discarded
        }
    };

    protected GRPCLogClientAppender(final String name,
                                    final Layout<? extends Serializable> layout,
                                    final Filter filter,
                                    final boolean ignoreExceptions,
                                    final Property[] properties) {
        super(
                name,
                layout,
                filter,
                ignoreExceptions,
                true,
                properties,
                getManager0(layout)
        );
    }

    @Override
    public void append(final LogEvent event) {
    }

    @PluginFactory
    public static GRPCLogClientAppender createAppender(@PluginAttribute("name") final String name,
                                                       @PluginElement("Layout") final Layout<? extends Serializable> layout,
                                                       @PluginElement("Filter") final Filter filter,
                                                       @PluginConfiguration final Configuration config,
                                                       @PluginAttribute("ignoreExceptions") final String ignore,
                                                       @PluginAttribute("properties") final Property[] properties) {
        String appenderName = name == null ? "gRPCLogClientAppender" : name;
        final boolean ignoreExceptions = "true".equalsIgnoreCase(ignore);
        return new GRPCLogClientAppender(appenderName, layout, filter, ignoreExceptions, properties);
    }

    private static OutputStreamManager getManager0(final Layout<? extends Serializable> layout) {
        return OutputStreamManager.getManager(NAME, new Object(), (s, o) -> new OutputStreamManager(DISCARDED_STREAM, STREAM_NAME, layout, false) {
        });
    }
}
