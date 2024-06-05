package com.cloth.backend.dto;


public class NoteEditDto extends AuthDto {

  private String id;
  private String name_note;
  private String note;
  private String picture;
  private String composition;
  private String color;
  private String season;
  private String insulation;
  private String type_of_landing;
  private String country_of_manufacture;
  private String type_of_fastener;
  private String type_of_pockets;
  private String equipment;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName_note() {
    return name_note;
  }

  public void setName_note(String name_note) {
    this.name_note = name_note;
  }

  public String getNote() {
    return note;
  }

  public void setNote(String note) {
    this.note = note;
  }

  public String getPicture() {
    return picture;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  public String getComposition() {
    return composition;
  }

  public void setComposition(String composition) {
    this.composition = composition;
  }

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
  }

  public String getSeason() {
    return season;
  }

  public void setSeason(String season) {
    this.season = season;
  }

  public String getInsulation() {
    return insulation;
  }

  public void setInsulation(String insulation) {
    this.insulation = insulation;
  }

  public String getType_of_landing() {
    return type_of_landing;
  }

  public void setType_of_landing(String type_of_landing) {
    this.type_of_landing = type_of_landing;
  }

  public String getCountry_of_manufacture() {
    return country_of_manufacture;
  }

  public void setCountry_of_manufacture(String country_of_manufacture) {
    this.country_of_manufacture = country_of_manufacture;
  }

  public String getType_of_fastener() {
    return type_of_fastener;
  }

  public void setType_of_fastener(String type_of_fastener) {
    this.type_of_fastener = type_of_fastener;
  }

  public String getType_of_pockets() {
    return type_of_pockets;
  }

  public void setType_of_pockets(String type_of_pockets) {
    this.type_of_pockets = type_of_pockets;
  }

  public String getEquipment() {
    return equipment;
  }

  public void setEquipment(String equipment) {
    this.equipment = equipment;
  }

  @Override
  public String toString() {
    return "NoteEditDto{" +
            "id='" + id + '\'' +
            ", name_note='" + name_note + '\'' +
            ", note='" + note + '\'' +
            ", picture='" + picture + '\'' +
            ", composition='" + composition + '\'' +
            ", color='" + color + '\'' +
            ", season='" + season + '\'' +
            ", insulation='" + insulation + '\'' +
            ", type_of_landing='" + type_of_landing + '\'' +
            ", country_of_manufacture='" + country_of_manufacture + '\'' +
            ", type_of_fastener='" + type_of_fastener + '\'' +
            ", type_of_pockets='" + type_of_pockets + '\'' +
            ", equipment='" + equipment + '\'' +
            '}';
  }
}
