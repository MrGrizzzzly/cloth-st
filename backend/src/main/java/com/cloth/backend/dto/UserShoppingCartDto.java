package com.cloth.backend.dto;

public class UserShoppingCartDto {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "UserShoppingCartDto{" +
                "id='" + id + '\'' +
                '}';
    }
}
