package com.cloth.backend.repositories;

import com.cloth.backend.models.User;
import com.cloth.backend.models.UserShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для взаимодействия с данными о карусели джинсов в базе данных.
 */
@Repository
public interface UserShoppingCartRepository extends JpaRepository<UserShoppingCart, Integer> {

    List<UserShoppingCart> findByUser(User user);
}
