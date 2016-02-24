package com.company.dashboard.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

/**
 * Represents a user in the system.
 */
@Entity
@NamedQuery(name = "User.updateUserStatus",
    query = "update User set isActive=true where id=:userId")
@Where(clause = "isActive='true'")
@SQLDelete(sql = "update user_account set isActive=false where user_id=?")
@Table(name = "USER_ACCOUNT")
public class User {

  @Column(name = "USER_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "USERNAME")
  private String username;

  @Column(name = "LOGIN_ID", unique = true)
  private String loginId;

  @Column(name = "PASSWORD", nullable = false)
  private String password;

  @Column(name = "isActive", nullable = false)
  private boolean isActive;

  @Column(name = "createdOn", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdOn;

  @Column(name = "updatedOn", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedOn;

  protected User() {}

  /**
   * Initializes a user.
   * 
   * @param loginId Email id of the user.
   * @param password Password of the user.
   * @param username Display name of the user.
   */
  public User(String loginId, String password, String username) {
    Date current = new Date();
    this.loginId = loginId;
    this.password = password;
    this.createdOn = current;
    this.updatedOn = current;
    this.username = username;
  }

  /**
   * Initializes a user.
   * 
   * @param loginId Email id of the user.
   * @param password Password of the user.
   * @param username Display name of the user.
   * @param isActive Whether a user is active or not.
   */
  public User(String loginId, String password, String username, boolean isActive) {
    this(loginId, password, username);
    this.isActive = isActive;
  }

  public Long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public Boolean getIsActive() {
    return isActive;
  }

  public void setIsActive(boolean isActive) {
    this.isActive = isActive;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Date getUpdatedOn() {
    return updatedOn;
  }

  @PrePersist
  public void onPersist() {
    this.createdOn = new Date();
    this.updatedOn = new Date();
  }

  @PreUpdate
  public void onUpdate() {
    this.updatedOn = new Date();
  }
}
