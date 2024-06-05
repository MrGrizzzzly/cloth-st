package com.cloth.backend.repositories;

import com.cloth.backend.models.CarouselNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Репозиторий для взаимодействия с данными о карусели джинсов в базе данных.
 */
@Repository
public interface CarouselNoteRepository extends JpaRepository<CarouselNotes, Integer> {

}
