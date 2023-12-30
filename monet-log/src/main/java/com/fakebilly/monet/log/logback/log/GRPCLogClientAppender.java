package com.fakebilly.monet.log.logback.log;

import ch.qos.logback.core.OutputStreamAppender;

import java.io.OutputStream;

/**
 * GRPCLogClientAppender
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly1us/monet
 **/
public class GRPCLogClientAppender<E> extends OutputStreamAppender<E> {

    public GRPCLogClientAppender() {
    }

    @Override
    public void start() {
        setOutputStream(new OutputStream() {
            @Override
            public void write(final int b) {
                // discarded
            }
        });
        super.start();
    }

    @Override
    protected void subAppend(final E event) {
    }
}
