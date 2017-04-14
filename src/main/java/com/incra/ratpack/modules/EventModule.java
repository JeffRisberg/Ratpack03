package com.incra.ratpack.modules;

import com.google.inject.AbstractModule;
import com.incra.ratpack.handlers.EventHandler;
import com.incra.ratpack.handlers.MetricHandler;

/**
 * @author Jeff Risberg
 * @since 04/12/17
 */
public class EventModule extends AbstractModule {
    protected void configure() {
        bind(EventHandler.class);
    }
}
