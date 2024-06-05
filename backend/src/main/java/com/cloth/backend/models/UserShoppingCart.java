package com.cloth.backend.models;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "usershoppingcart")
public class UserShoppingCart {
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
    @JoinColumn(name = "nid")
    private Notes note;

    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;

    /**
     * Пустой конструктор.
     */
    public UserShoppingCart() {
        this.id = 0;
        this.note = new Notes();
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserShoppingCart that = (UserShoppingCart) o;

        if (id != that.id) return false;
        if (!Objects.equals(note, that.note)) return false;
        return Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "UserShoppingCart{" +
                "id=" + id +
                ", note=" + note +
                ", user=" + user +
                '}';
    }
}
