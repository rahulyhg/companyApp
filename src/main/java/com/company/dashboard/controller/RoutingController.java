package com.company.dashboard.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Spring controller for providing the resources to a routed url. For example: login url will fetch
 * login.html which is provided by this controller.
 */
@Controller
public class RoutingController {

  private static final Logger logger = Logger.getLogger(RoutingController.class);

  /**
   * Maps the resource of login url to login.html.
   * 
   * @return A Login html url.
   */
  @RequestMapping(value = "/login")
  public String login() {
    logger.info("Fetching login html");
    return "resources/login.html";
  }

  /**
   * Maps a dashboard html to dashboard url.
   * 
   * @return A dashboard html url.
   */
  @RequestMapping(value = "/dashboard")
  public String dashboard() {
    logger.info("Fetching dashboard html");
    return "resources/dashboard.html";
  }
}
