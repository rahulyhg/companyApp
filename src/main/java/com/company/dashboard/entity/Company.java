package com.company.dashboard.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Represents the company table.
 *
 */
@Entity
@Table(name = "COMPANY")
public class Company {

  @Column(name = "COMPANY_ID")
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "NAME")
  private String name;

  @Column(name = "ADDRESS")
  private String address;

  @Column(name = "CITY")
  private String city;

  @Column(name = "COUNTRY")
  private String country;

  @Column(name = "EMAIL")
  private String email;

  @Column(name = "PHONE")
  private String phone;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @JoinTable(name = "owners", joinColumns = {@JoinColumn(name = "COMPANY_ID")},
      inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
  private Set<User> owners;

  @Column(name = "createdOn", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdOn;

  @Column(name = "updatedOn", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedOn;

  public Company() {};

  public Company(String name, String address, String country, String email, String phone,
      Set<User> owners, String city) {
    this.name = name;
    this.address = address;
    this.country = country;
    this.email = email;
    this.phone = phone;
    this.owners = owners;
    Date current = new Date();
    createdOn = current;
    updatedOn = current;
    this.city = city;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getCity() {
    return city;
  }

  public String getCountry() {
    return country;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  public Set<User> getOwners() {
    if (owners == null) {
      owners = new HashSet<User>();
    }
    return owners;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public void setOwners(Set<User> owners) {
    this.owners = owners;
  }

  public Date getCreatedOn() {
    return createdOn;
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
