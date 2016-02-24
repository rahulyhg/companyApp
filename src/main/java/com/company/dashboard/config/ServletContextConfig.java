package com.company.dashboard.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Spring MVC config for the servlet context in the application. The beans of this context are only
 * visible inside the servlet context.
 */
@Configuration
@EnableWebMvc
@ComponentScan("com.company.dashboard.controller")
@Import({AppSecurityConfig.class})
public class ServletContextConfig extends WebMvcConfigurerAdapter {

  /**
   * Adds location of resource handlers to resource handler registry.
   */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
  }
}
