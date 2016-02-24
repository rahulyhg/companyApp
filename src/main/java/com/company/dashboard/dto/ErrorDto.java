package com.company.dashboard.dto;

public class ErrorDto {

  private long code;
  private String message;

  public ErrorDto() {}

  public ErrorDto(String message) {
    this.message = message;
  }

  public long getCode() {
    return code;
  }

  public void setCode(long code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
