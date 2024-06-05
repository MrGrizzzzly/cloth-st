package com.cloth.backend.dto;

import java.math.BigInteger;
import java.util.Objects;


public class PGDto extends AuthDto {
  private String p;
  private String g;

  public PGDto(String sessionId, String token, BigInteger p, BigInteger g, String username, String password, String role) {
    super(sessionId, token, username, password, role);
    this.p = p.toString();
    this.g = g.toString();
  }

  public String getP() {
    return p;
  }

  public void setP(String p) {
    this.p = p;
  }

  public String getG() {
    return g;
  }

  public void setG(String g) {
    this.g = g;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    PGDto that = (PGDto) o;

    if (!Objects.equals(p, that.p)) return false;
    return Objects.equals(g, that.g);
  }

  @Override
  public int hashCode() {
    int result = p != null ? p.hashCode() : 0;
    result = 31 * result + (g != null ? g.hashCode() : 0);
    return result;
  }


  @Override
  public String toString() {
    return super.toString() + "AuthInitDto{" +
            "p='" + p + '\'' +
            ", g='" + g + '\'' +
            '}';
  }
}
