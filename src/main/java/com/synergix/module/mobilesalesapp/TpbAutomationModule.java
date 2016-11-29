/**
 *
 */
package com.synergix.module.mobilesalesapp;

import com.google.inject.AbstractModule;

/**
 * @author <a href="mailto:haithanh0809@gmail.com">Nguyen Thanh Hai</a>
 * Sep 30, 2016
 */
public class TpbAutomationModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(DriverProvider.class);
    bind(PageProvider.class);
    bind(ObjectRepositoryProvider.class);
  }

}
