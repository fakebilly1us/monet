package com.fakebilly.monet.prometheus.event;

import org.springframework.context.ApplicationEvent;

/**
 * AfterInitEvent
 * @author FakeBilly
 * @version V1.0.0
 * @github https://github.com/fakebilly-dev/monet
 **/
public class AfterInitEvent extends ApplicationEvent {

    private static final long serialVersionUID = 2446905022345480452L;

    /**
     * Create a new ApplicationEvent.
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public AfterInitEvent(Object source) {
        super(source);
    }


}
