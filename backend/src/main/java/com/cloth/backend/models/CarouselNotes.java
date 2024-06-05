package com.cloth.backend.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "carousel")
public class CarouselNotes {


  /**
   * Уникальный идентификатор картинки.
   */
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  /**
   * Уникальный идентификатор джинсов.
   */
  @ManyToOne
  @JoinColumn(name = "uid")
  private Notes note;

  /**
   * Картинки.
   */
  @Column(name = "picture")
  private String picture;

  /**
   * Пустой конструктор.
   */
  public CarouselNotes() {
    this.id = 0;
    this.note = new Notes();
    this.picture = "";
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
   * геттер джинсов
   *
   * @return note
   */
  public Notes getNote() {
    return note;
  }

  /**
   * сеттер джинсов
   *
   * @param note note
   */
  public void setNote(Notes note) {
    this.note = note;
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
   * @param picture picture
   */
  public void setPicture(String picture) {
    this.picture = picture;
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

    CarouselNotes that = (CarouselNotes) o;

    if (id != that.id) return false;
    if (!Objects.equals(note, that.note)) return false;
    return Objects.equals(picture, that.picture);
  }

  /**
   * Получения хэш-кода
   *
   * @return хэш
   */
  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (note != null ? note.hashCode() : 0);
    result = 31 * result + (picture != null ? picture.hashCode() : 0);
    return result;
  }

  /**
   * Строковое представление
   * @return строковое представление
   */
  @Override
  public String toString() {
    return "CarouselNotes{" +
            "id=" + id +
            ", note=" + note +
            ", picture='" + picture + '\'' +
            '}';
  }
}