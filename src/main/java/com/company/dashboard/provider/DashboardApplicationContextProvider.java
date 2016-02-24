package com.company.dashboard.provider;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Provides Application Context at runtime.
 */
@Component
public class DashboardApplicationContextProvider implements ApplicationContextAware {

  private static ApplicationContext appContext;

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    if (appContext != null) {
      throw new IllegalArgumentException("ApplicationContext cannot be changed.");
    }
    appContext = applicationContext;
  }

  public static ApplicationContext getApplicationContext() {
    return appContext;
  }
}
