package com.cloth.backend.models;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

/**
 * Модель пользователя.
 * Сущность, представляющая информацию о пользователе в системе.
 */
@Entity
@Table(name = "users")
public class User {

  /**
   * Уникальный идентификатор пользователя.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  /**
   * Имя пользователя.
   */
  @Column(name = "username")
  @NotEmpty
  private String username;

  /**
   * Пароль пользователя.
   */
  @NotEmpty
  @Column(name = "u_password")
  private String password;

  /**
   * Токен пользователя.
   */
  @NotEmpty
  @Column(name = "token")
  private String token;

  /**
   * Роль пользователя.
   */
  @NotEmpty
  @Column(name = "role")
  private String role;

  /**
   * Пустой конструктор.
   */
  public User() {
    this.username = "";
    this.password = "";
    this.token = "";
    this.role = "User";
  }
  /**
   * Конструктор для создания объекта пользователя с заданными параметрами.
   *
   * @param username Имя пользователя.
   * @param password Пароль пользователя.
   * @param token    Токен пользователя.
   * @param role     Роль пользователя.
   */
  public User(String username, String password, String token, String role) {
    this.username = username;
    this.password = password;
    this.token = token;
    this.role = role;
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
   * геттер имени
   *
   * @return имя
   */
  public String getUsername() {
    return username;
  }

  /**
   * сеттер имени
   *
   * @param username имя
   */

  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * геттер пароля
   *
   * @return пароль
   */
  public String getPassword() {
    return password;
  }

  /**
   * сеттер пароля
   *
   * @param password пароль
   */
  public void setPassword(String password) {
    this.password = password;
  }

  /**
   * геттер токена
   *
   * @return токен
   */
  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  /**
   * геттер роли
   *
   * @return роль
   */
  public String getRole() {
    return role;
  }

  /**
   * сеттер роли
   *
   * @param role роль
   */
  public void setRole(String role) {
    this.role = role;
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

    User user = (User) o;

    if (id != user.id) return false;
    if (!Objects.equals(username, user.username)) return false;
    if (!Objects.equals(password, user.password)) return false;
    if (!Objects.equals(token, user.token)) return false;
    return Objects.equals(role, user.role);
  }

  /**
   * Получения хэш-кода
   *
   * @return хэш
   */
  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (username != null ? username.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (token != null ? token.hashCode() : 0);
    result = 31 * result + (role != null ? role.hashCode() : 0);
    return result;
  }

  /**
   * Строковое представление
   * @return строковое представление
   */
  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", token='" + token + '\'' +
            ", role='" + role + '\'' +
            '}';
  }
}

