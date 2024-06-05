package com.cloth.backend.dto;

public class UserShoppingCartEditDto extends AuthDto {
  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return "UserShoppingCartEditDto{" +
            "id='" + id + '\'' +
            '}';
  }
}
