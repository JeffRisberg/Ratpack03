package com.incra.ratpack.modules;

import com.google.inject.AbstractModule;
import com.incra.ratpack.handlers.DonationHandler;

/**
 * @author Jeff Risberg
 * @since 02/12/17
 */
public class DonationModule extends AbstractModule {
  protected void configure() {
    bind(DonationHandler.class);
  }
}
