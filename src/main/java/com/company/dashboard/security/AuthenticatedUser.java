package com.company.dashboard.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * This represents a logged in user in spring context.
 */
@SuppressWarnings("serial")
public class AuthenticatedUser implements UserDetails {

  private long userId;
  private String username;
  private boolean isEnabled;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;

  public AuthenticatedUser() {}

  /**
   * Initializes an AuthenticatedUser.
   * 
   * @param username Username of user.
   * @param password Encrypted password of user.
   * @param isEnabled Determines whether a user is active or not.
   * @param authorities A collection of authorities/privileges (Spring based) to user.
   */
  public AuthenticatedUser(String username, String password, boolean isEnabled,
      Collection<? extends GrantedAuthority> authorities, long userId) {
    this.username = username;
    this.password = password;
    this.isEnabled = isEnabled;
    this.authorities = authorities;
    this.userId = userId;
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  public String getPassword() {
    return password;
  }

  public String getUsername() {
    return username;
  }

  public boolean isAccountNonExpired() {
    return true;
  }

  public boolean isAccountNonLocked() {
    return true;
  }

  public boolean isCredentialsNonExpired() {
    return true;
  }

  public boolean isEnabled() {
    return isEnabled;
  }

  public long userId() {
    return userId;
  }
}
