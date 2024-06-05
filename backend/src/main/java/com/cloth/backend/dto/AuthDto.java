package com.cloth.backend.dto;


import java.util.Objects;
import jakarta.validation.constraints.NotEmpty;


public class AuthDto {

  private String token;

  private String sessionId;

  @NotEmpty
  private String username;
  @NotEmpty
  private String password;

  @NotEmpty
  private String role;

  public AuthDto() {
    this.sessionId = "";
    this.token = "";
    this.username = "";
    this.password = "";
    this.role = "";
  }

  public AuthDto(String sessionId, String token, String username, String password, String role) {
    this.sessionId = sessionId;
    this.token = token;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }


  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    AuthDto authDto = (AuthDto) o;

    if (!Objects.equals(token, authDto.token)) return false;
    if (!Objects.equals(sessionId, authDto.sessionId)) return false;
    if (!Objects.equals(username, authDto.username)) return false;
    if (!Objects.equals(password, authDto.password)) return false;
    return Objects.equals(role, authDto.role);
  }

  @Override
  public int hashCode() {
    int result = token != null ? token.hashCode() : 0;
    result = 31 * result + (sessionId != null ? sessionId.hashCode() : 0);
    result = 31 * result + (username != null ? username.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (role != null ? role.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "AuthDto{" +
            "token='" + token + '\'' +
            ", sessionId='" + sessionId + '\'' +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", role='" + role + '\'' +
            '}';
  }
}