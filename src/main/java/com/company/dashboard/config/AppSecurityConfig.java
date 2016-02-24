package com.company.dashboard.config;


import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;

import com.allanditzel.springframework.security.web.csrf.CsrfTokenResponseHeaderBindingFilter;
import com.company.dashboard.security.SecurityUserDetailsService;

/**
 * The Spring Security configuration for the application - its a form login config with
 * authentication via session cookie (once logged in), with fallback to HTTP Basic for non-browser
 * clients.The CSRF token is put on the reply as a header via a filter, as there is no server-side
 * rendering on this app.
 */
@Configuration
@EnableWebMvcSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

  private static final Logger logger = Logger.getLogger(AppSecurityConfig.class);

  @Autowired
  private SecurityUserDetailsService userDetailsService;
  @Autowired
  private DataSource dataSource;

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    CsrfTokenResponseHeaderBindingFilter csrfTokenFilter =
        new CsrfTokenResponseHeaderBindingFilter();
    http.addFilterAfter(csrfTokenFilter, CsrfFilter.class);
    http.authorizeRequests()
        .antMatchers("/resources/config/**")
        .permitAll()
        .antMatchers("/resources/fonts/**")
        .permitAll()
        .antMatchers("/resources/images/**")
        .permitAll()
        .antMatchers("/resources/js/**")
        .permitAll()
        .antMatchers("/resources/style/**")
        .permitAll()
        .antMatchers("/resources/templates/**")
        .permitAll()
        .antMatchers("/resources/login.html")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/user")
        .permitAll()
        .antMatchers(HttpMethod.HEAD, "/login")
        .permitAll()
        .antMatchers(HttpMethod.GET, "/login")
        .permitAll()
        .anyRequest().permitAll();

        // Uncomment below loc to enable form based login.
//        .authenticated()
//        .and()
//        .formLogin()
//        .defaultSuccessUrl("/dashboard")
//        .loginProcessingUrl("/authenticate")
//        .usernameParameter("username")
//        .passwordParameter("password")
//        .successHandler(
//            new AjaxAuthenticationSuccessHandler(
//                new SavedRequestAwareAuthenticationSuccessHandler())).loginPage("/login").and()
//        .httpBasic().and().logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll();
  }

}
