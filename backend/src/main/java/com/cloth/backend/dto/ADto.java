package com.cloth.backend.dto;

import java.math.BigInteger;


public class ADto extends AuthDto {
  private String A;

  public ADto(String sessionId, String token, BigInteger A, String username, String password, String role) {
    super(sessionId, token, username, password, role);
    this.A = A.toString();
  }

  public String getA() {
    return A;
  }

  public void setA(String a) {
    A = a;
  }

  @Override
  public String toString() {
    return super.toString() + "AuthFinDto{" +
            "A='" + A + '\'' +
            '}';
  }
}
