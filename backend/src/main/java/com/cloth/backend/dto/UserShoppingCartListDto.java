package com.cloth.backend.dto;

import java.util.List;
import java.util.Objects;

public class UserShoppingCartListDto {
    List<UserShoppingCartDto> userShoppingCartList;

    public UserShoppingCartListDto(List<UserShoppingCartDto> userShoppingCartList) {
        this.userShoppingCartList = userShoppingCartList;
    }

    public List<UserShoppingCartDto> getUserShoppingCartList() {
        return userShoppingCartList;
    }

    public void setUserShoppingCartList(List<UserShoppingCartDto> userShoppingCartList) {
        this.userShoppingCartList = userShoppingCartList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserShoppingCartListDto that = (UserShoppingCartListDto) o;

        return Objects.equals(userShoppingCartList, that.userShoppingCartList);
    }

    @Override
    public int hashCode() {
        return userShoppingCartList != null ? userShoppingCartList.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "UserShoppingCartListDto{" +
                "userShoppingCartList=" + userShoppingCartList +
                '}';
    }
}
