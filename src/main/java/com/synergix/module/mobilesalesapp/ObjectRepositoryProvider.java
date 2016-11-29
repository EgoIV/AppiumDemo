/**
 * 
 */
package com.synergix.module.mobilesalesapp;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.inject.Singleton;

/**
 * @author <a href="mailto:haint21@fsoft.com.vn">Nguyen Thanh Hai</a>
 *
 * Oct 28, 2016
 */
@Singleton
public class ObjectRepositoryProvider {

  public Properties get(String repoFile) throws IOException {
    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("object/repo/" + repoFile);
    Properties repo = new Properties();
    repo.load(is);
    is.close();
    return repo;
  }
  
  public Properties getProperties(String repoFile) throws IOException {
	    InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(repoFile);
	    Properties repo = new Properties();
	    repo.load(is);
	    is.close();
	    return repo;
	  }
}
