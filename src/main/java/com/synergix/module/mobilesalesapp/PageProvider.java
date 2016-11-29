/**
 * 
 */
package com.synergix.module.mobilesalesapp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import com.synergix.module.mobilesalesapp.pages.AbstractPage;
import com.synergix.module.mobilesalesapp.pages.LoginPage;

/**
 * @author <a href="mailto:haint21@fsoft.com.vn">Nguyen Thanh Hai</a>
 *
 * Oct 28, 2016
 */
@Singleton
public class PageProvider {
  
  @Inject 
  private DriverProvider driver;
  
  @Inject
  private ObjectRepositoryProvider objRepoProvider;
  
  private Map<Class<?>, Object> cache = new HashMap<Class<?>, Object>();
	
  public <T extends AbstractPage> T get(Class<T> type) throws IOException {
    if (type.isAssignableFrom(LoginPage.class))
      if (cache.containsKey(type)) return type.cast(cache.get(type));
      else {
    	  LoginPage page = new LoginPage(driver.get(), objRepoProvider);
        cache.put(type, page);
        return type.cast(page);
      }
//    else if (type.isAssignableFrom(EBankPage.class))
//      if (cache.containsKey(type)) return type.cast(cache.get(type));
//      else {
//        EBankPage page = new EBankPage(webDriverProvider.get(), objRepoProvider);
//        cache.put(type, page);
//        return type.cast(page);
//      }
    else
      throw new UnsupportedOperationException("The " + type + " has not yet handled.");
  }
}
