package com.cloth.backend.controllers;


import com.cloth.backend.dto.*;
import com.cloth.backend.exceptions.notCreated.NoteNotCreatedException;
import com.cloth.backend.exceptions.notFound.NoteNotFoundException;
import com.cloth.backend.services.CarouselNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Контроллер для взаимодействия с каруселью джинсов.
 */
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/carousel/")
public class CarouselNoteController {

  /**
   * Сервис для обработки запросов карусели.
   */
  private final CarouselNoteService service;


  /**
   * Конструктор контроллера.
   *
   * @param service  сервис карусели джинсов
   */
  @Autowired
  public CarouselNoteController(CarouselNoteService service) {
    this.service = service;;
  }


  /**
   * Обрабатывает запрос на получение списка джинсов от конкретного пользователя по id джинсов.
   *
   * @param token токен пользователя
   * @param id идентификатор джинсов
   * @return  представление списка джинсов
   */
  @PostMapping("/{id}")
  public ResponseEntity<CarouselNoteListDto> getById(@RequestBody AuthDto token, @PathVariable("id") int id) {
    try {

      return new ResponseEntity<>(service.findById(token, id), HttpStatus.OK);
    } catch (Exception e) {
      throw new NoteNotFoundException(e.getMessage());
    }
  }

  /**
   * Обрабатывает запрос на создание информации об джинсах.
   *
   * @param noteCreateDto DTO с зашифрованными данными джинсов
   * @return  представление для редактирования
   */
  @PostMapping("/new/{id}")
  public ResponseEntity<CarouselNoteDto> newCarousel(@RequestBody CarouselNoteEditDto noteCreateDto, @PathVariable("id") int id) {

    try {
      return new ResponseEntity<>(service.save(noteCreateDto, id), HttpStatus.OK);
    } catch (Exception e) {
      throw new NoteNotCreatedException(e.getMessage());
    }
  }
}