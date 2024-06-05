package com.cloth.backend.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cloth.backend.models.Notes;
import com.cloth.backend.models.User;

/**
 * Репозиторий для взаимодействия с данными о джинсах в базе данных.
 */
@Repository
public interface NoteRepository extends JpaRepository<Notes, Integer> {

  /**
   * Метод для поиска музыкальных инструментов, принадлежащих указанному пользователю.
   *
   * @param user Пользователь, чьи музыкальные инструменты необходимо найти.
   * @return Список музыкальных инструментов пользователя.
   */
  List<Notes> findByUser(User user);

}
