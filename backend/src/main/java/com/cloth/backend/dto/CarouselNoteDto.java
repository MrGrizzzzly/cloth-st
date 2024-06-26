package com.cloth.backend.dto;


public class CarouselNoteDto {
  private String id;
  private String picture;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
  public String getPicture() {
    return picture;
  }
  public void setPicture(String picture) {
    this.picture = picture;
  }

  @Override
  public String toString() {
    return "CarouselNoteDto{" +
            "id='" + id + '\'' +
            ", picture='" + picture + '\'' +
            '}';
  }
}
