package com.company.dashboard.dto;

import java.util.Date;

public class UserDto {

  private Long id;
  private String username;
  private String loginId;
  private String password;
  private boolean isActive;
  private Date createdOn;
  private Date updatedOn;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getLoginId() {
    return loginId;
  }

  public void setLoginId(String loginId) {
    this.loginId = loginId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean isActive) {
    this.isActive = isActive;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  public void setUpdatedOn(Date updatedOn) {
    this.updatedOn = updatedOn;
  }

  public Date getUpdatedOn() {
    return updatedOn;
  }

  public String toString() {
    StringBuilder userStr = new StringBuilder();
    userStr.append("User name: ").append(this.getUsername());
    return userStr.toString();
  }
}
