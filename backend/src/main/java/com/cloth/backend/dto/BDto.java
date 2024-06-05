package com.cloth.backend.dto;

import java.math.BigInteger;


public class BDto extends AuthDto {
  private String B;


  public BDto(String sessionId, String token, BigInteger B, String username, String password, String role) {
    super(sessionId, token, username, password, role);
    this.B = B.toString();
  }

  public String getB() {
    return B;
  }

  public void setB(String b) {
    B = b;
  }

  @Override
  public String toString() {
    return super.toString() + "AuthFinRespDto{" +
            "B='" + B + '\'' +
            '}';
  }
}
