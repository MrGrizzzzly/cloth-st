package com.cloth.backend.controllers;

import com.cloth.backend.exceptions.notCreated.NoteNotCreatedException;
import com.cloth.backend.exceptions.notDeleted.NoteNotDeletedException;
import com.cloth.backend.exceptions.notFound.NoteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.cloth.backend.dto.NoteDto;
import com.cloth.backend.dto.NoteEditDto;
import com.cloth.backend.dto.NoteListDto;
import com.cloth.backend.dto.AuthDto;
import com.cloth.backend.services.NoteService;


/**
 * Контроллер для взаимодействия с джинсами.
 */
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/note")
public class NoteController {


  /**
   * Сервис для обработки запросов джинсов.
   */
  private final NoteService service;


  /**
   * Конструктор контроллера.
   *
   * @param service сервис джинсов
   */
  @Autowired
  public NoteController(NoteService service) {
    this.service = service;
  }


  /**
   * Обрабатывает запрос на получение списка джинсов от конкретного пользователя.
   *
   * @param token токен пользователя
   * @return  представление списка джинсов
   */
  @PostMapping("/byUser")
  public ResponseEntity<NoteListDto> getAllByUser(@RequestBody AuthDto token) {
    try {
      return new ResponseEntity<>(service.findByUser(token), HttpStatus.OK);
    } catch (Exception e) {
      throw new NoteNotFoundException(e.getMessage());
    }
  }

  /**
   * Обрабатывает запрос на получение списка джинсов.
   *
   * @param token токен пользователя
   * @return  представление списка джинсов
   */
  @PostMapping("/all")
  public ResponseEntity<NoteListDto> getAll(@RequestBody AuthDto token) {
    try {
      return new ResponseEntity<>(service.findByAll(token), HttpStatus.OK);
    } catch (Exception e) {
      throw new NoteNotFoundException(e.getMessage());
    }
  }

  /**
   * Обрабатывает запрос на удаление информации об джинсах.
   *
   * @param token токен пользователя
   * @return  представление для редактирования
   */
  @PostMapping("/del")
  public ResponseEntity<NoteDto> delNote(@RequestBody NoteEditDto token) {
    try {
      service.delete(token);
    } catch (Exception e) {
      throw new NoteNotDeletedException(e.getMessage());
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

  /**
   * Обрабатывает запрос на редактирование информации об джинсах.
   *
   * @param noteEditDto DTO с зашифрованными данными джинсов
   * @return  представление для редактирования
   */
  @PostMapping("/edit")
  public ResponseEntity<NoteDto> editNote(@RequestBody NoteEditDto noteEditDto){
    try {
      return new ResponseEntity<>(service.update(noteEditDto), HttpStatus.OK);
    } catch (Exception e) {
      throw new NoteNotCreatedException(e.getMessage());
    }
  }

  /**
   * Обрабатывает запрос на создание информации об джинсах.
   *
   * @param noteCreateDto DTO с зашифрованными данными джинсов
   * @return  представление для редактирования
   */
  @PostMapping("/new")
  public ResponseEntity<NoteDto> newNote(@RequestBody NoteEditDto noteCreateDto) {
    try {
      return new ResponseEntity<>(service.save(noteCreateDto), HttpStatus.OK);
    } catch (Exception e) {
      throw new NoteNotCreatedException(e.getMessage());
    }
  }
}
