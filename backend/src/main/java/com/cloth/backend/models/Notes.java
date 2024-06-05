package com.cloth.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

import java.util.Objects;

/**
 * Модель джинсов.
 * Сущность, представляющая информацию о джинсах в системе.
 */
@Entity
@Table(name = "notes")
public class Notes {

  /**
   * Уникальный идентификатор джинсов.
   */
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  /**
   * Название.
   */
  @Column(name = "name_note")
  private String name_note;

  /**
   * Стоимость.
   */
  @Column(name = "note")
  private String note;

  /**
   * Добавивший пользователь.
   */
  @ManyToOne
  @JoinColumn(name = "uid")
  private User user;

  /**
   * Картинка.
   */
  @Column(name = "picture")
  private String picture;

  /**
   * Состав.
   */
  @Column(name = "composition")
  private String composition;

  /**
   * Цвет.
   */
  @Column(name = "color")
  private String color;

  /**
   * Сезон.
   */
  @Column(name = "season")
  private String season;

  /**
   * Утеплитель.
   */
  @Column(name = "insulation")
  private String insulation;

  /**
   * Тип посадки.
   */
  @Column(name = "typeoflanding")
  private String type_of_landing;

  /**
   * Страна.
   */
  @Column(name = "Countryofmanufacture")
  private String country_of_manufacture;

  /**
   * Вид застежки.
   */
  @Column(name = "typeoffastener")
  private String type_of_fastener;

  /**
   * Тип карманов.
   */
  @Column(name = "typeofpockets")
  private String type_of_pockets;

  /**
   * Комплектация.
   */
  @Column(name = "equipment")
  private String equipment;

  /**
   * Пустой конструктор.
   */
  public Notes() {
    this.id = 0;
    this.name_note = "";
    this.note = "";
    this.picture = "";
    this.composition = "";
    this.color="";
    this.season="";
    this.insulation="";
    this.type_of_landing="";
    this.country_of_manufacture="";
    this.type_of_fastener="";
    this.type_of_pockets="";
    this.equipment="";
    this.user = new User();
  }


  /**
   * геттер id
   *
   * @return id
   */
  public int getId() {
    return id;
  }

  /**
   * сеттер id
   *
   * @param id id
   */
  public void setId(int id) {
    this.id = id;
  }


  /**
   * геттер название
   *
   * @return название
   */
  public String getName_note() {
    return name_note;
  }

  /**
   * сеттер название
   *
   * @param name_note название
   */
  public void setName_note(String name_note) {
    this.name_note = name_note;
  }

  /**
   * геттер стоимости
   *
   * @return стоимость
   */
  public String getNote() {
    return note;
  }

  /**
   * сеттер стоимости
   *
   * @param note стоимость
   */
  public void setNote(String note) {
    this.note = note;
  }


  /**
   * геттер пользователя
   *
   * @return пользователь
   */
  public User getUser() {
    return user;
  }

  /**
   * сеттер пользователя
   *
   * @param user пользователь
   */
  public void setUser(User user) {
    this.user = user;
  }

  /**
   * геттер картинки
   *
   * @return picture
   */
  public String getPicture() {
    return picture;
  }

  /**
   * сеттер картинки
   *
   * @param picture1 picture
   */
  public void setPicture(String picture1) {
    this.picture = picture1;
  }

  /**
   * геттер состава
   *
   * @return composition
   */
  public String getComposition() {
    return composition;
  }


  /**
   * сеттер состава
   *
   * @param composition состав
   */
  public void setComposition(String composition) {
    this.composition = composition;
  }

  /**
   * геттер цвета
   *
   * @return color цвет
   */
  public String getColor() {
    return color;
  }

  /**
   * сеттер цвета
   *
   * @param color цвет
   */
  public void setColor(String color) {
    this.color = color;
  }

  /**
   * геттер сезона
   *
   * @return color сезон
   */
  public String getSeason() {
    return season;
  }

  /**
   * сеттер сезона
   *
   * @param season сезон
   */
  public void setSeason(String season) {
    this.season = season;
  }

  /**
   * геттер утеплителя
   *
   * @return insulation утеплитель
   */
  public String getInsulation() {
    return insulation;
  }

  /**
   * сеттер утеплителя
   *
   * @param insulation утеплитель
   */
  public void setInsulation(String insulation) {
    this.insulation = insulation;
  }

  /**
   * геттер типа посадки
   *
   * @return type_of_landing тип посадки
   */
  public String getType_of_landing() {
    return type_of_landing;
  }

  /**
   * сеттер типа посадки
   *
   * @param type_of_landing тип посадки
   */
  public void setType_of_landing(String type_of_landing) {
    this.type_of_landing = type_of_landing;
  }

  /**
   * геттер страны производства
   *
   * @return type_of_landing страна производства
   */
  public String getCountry_of_manufacture() {
    return country_of_manufacture;
  }

  /**
   * сеттер страны производства
   *
   * @param country_of_manufacture страна производства
   */
  public void setCountry_of_manufacture(String country_of_manufacture) {
    this.country_of_manufacture = country_of_manufacture;
  }

  /**
   * геттер типа застежки
   *
   * @return type_of_landing тип застежки
   */
  public String getType_of_fastener() {
    return type_of_fastener;
  }

  /**
   * сеттер типа застежки
   *
   * @param type_of_fastener тип застежки
   */
  public void setType_of_fastener(String type_of_fastener) {
    this.type_of_fastener = type_of_fastener;
  }

  /**
   * геттер типа карманов
   *
   * @return type_of_landing тип карманов
   */
  public String getType_of_pockets() {
    return type_of_pockets;
  }

  /**
   * сеттер типа карманов
   *
   * @param type_of_pockets тип застежки
   */
  public void setType_of_pockets(String type_of_pockets) {
    this.type_of_pockets = type_of_pockets;
  }

  /**
   * геттер комплектации
   *
   * @return equipment комплектации
   */
  public String getEquipment() {
    return equipment;
  }

  /**
   * сеттер комплектации
   *
   * @param equipment комплектация
   */
  public void setEquipment(String equipment) {
    this.equipment = equipment;
  }


  /**
   * Метод для сравнения
   *
   * @param o экземпляр, с которым будет происходить сравнение
   * @return являются ли экземпляры эквивалентными
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Notes notes = (Notes) o;

    if (id != notes.id) return false;
    if (!Objects.equals(name_note, notes.name_note)) return false;
    if (!Objects.equals(note, notes.note)) return false;
    if (!Objects.equals(user, notes.user)) return false;
    if (!Objects.equals(picture, notes.picture)) return false;
    if (!Objects.equals(composition, notes.composition)) return false;
    if (!Objects.equals(color, notes.color)) return false;
    if (!Objects.equals(season, notes.season)) return false;
    if (!Objects.equals(insulation, notes.insulation)) return false;
    if (!Objects.equals(type_of_landing, notes.type_of_landing))
      return false;
    if (!Objects.equals(country_of_manufacture, notes.country_of_manufacture))
      return false;
    if (!Objects.equals(type_of_fastener, notes.type_of_fastener))
      return false;
    if (!Objects.equals(type_of_pockets, notes.type_of_pockets))
      return false;
    return Objects.equals(equipment, notes.equipment);
  }

  /**
   * Получения хэш-кода
   *
   * @return хэш
   */
  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (name_note != null ? name_note.hashCode() : 0);
    result = 31 * result + (note != null ? note.hashCode() : 0);
    result = 31 * result + (user != null ? user.hashCode() : 0);
    result = 31 * result + (picture != null ? picture.hashCode() : 0);
    result = 31 * result + (composition != null ? composition.hashCode() : 0);
    result = 31 * result + (color != null ? color.hashCode() : 0);
    result = 31 * result + (season != null ? season.hashCode() : 0);
    result = 31 * result + (insulation != null ? insulation.hashCode() : 0);
    result = 31 * result + (type_of_landing != null ? type_of_landing.hashCode() : 0);
    result = 31 * result + (country_of_manufacture != null ? country_of_manufacture.hashCode() : 0);
    result = 31 * result + (type_of_fastener != null ? type_of_fastener.hashCode() : 0);
    result = 31 * result + (type_of_pockets != null ? type_of_pockets.hashCode() : 0);
    result = 31 * result + (equipment != null ? equipment.hashCode() : 0);
    return result;
  }


  /**
   * Строковое представление
   * @return строковое представление
   */
  @Override
  public String toString() {
    return "Notes{" +
            "id=" + id +
            ", name_note='" + name_note + '\'' +
            ", note='" + note + '\'' +
            ", user=" + user +
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
