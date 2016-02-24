package com.company.dashboard.dto;

import java.util.Date;
import java.util.Set;

public class CompanyDto {

  private Long id;
  private String name;
  private String address;
  private String city;
  private String country;
  private String email;
  private String phone;
  private Set<UserDto> owners;
  private Date createdOn;
  private Date updatedOn;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Set<UserDto> getOwners() {
    return owners;
  }

  public void setOwners(Set<UserDto> owners) {
    this.owners = owners;
  }

  public Date getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(Date createdOn) {
    this.createdOn = createdOn;
  }

  public Date getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(Date updatedOn) {
    this.updatedOn = updatedOn;
  }

  @Override
  public String toString() {
    StringBuilder companyStr = new StringBuilder();
    companyStr.append("Company Name: ").append(this.getName());
    companyStr.append("Company Id: ").append(this.getId());
    return companyStr.toString();
  }
}
