package com.incra.ratpack.modules;

import com.google.inject.AbstractModule;
import com.incra.ratpack.handlers.LoggingEventHandler;

/**
 * @author Jeff Risberg
 * @since 04/12/17
 */
public class LoggingEventModule extends AbstractModule {
    protected void configure() {
        bind(LoggingEventHandler.class);
    }
}
