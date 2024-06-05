package com.cloth.backend.controllers;

import com.cloth.backend.dto.*;
import com.cloth.backend.exceptions.notCreated.NoteNotCreatedException;
import com.cloth.backend.exceptions.notDeleted.NoteNotDeletedException;
import com.cloth.backend.exceptions.notFound.NoteNotFoundException;
import com.cloth.backend.services.UserShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * Контроллер для взаимодействия с корзиной пользователя.
 */
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/usershoppingcart/")
public class UserShoppingCartController {

  /**
   * Сервис для обработки запросов корзины.
   */
  private final UserShoppingCartService service;


  @Autowired
  public UserShoppingCartController(UserShoppingCartService service) {
    this.service = service;
  }

  @PostMapping("/byUser")
  public ResponseEntity<NoteListDto> getByUser(@RequestBody AuthDto token) {
    try {
      return new ResponseEntity<>(service.findByUser(token), HttpStatus.OK);
    } catch (Exception e) {
      throw new NoteNotFoundException(e.getMessage());
    }
  }

  @PostMapping("/byIds")
  public ResponseEntity<UserShoppingCartListDto> getByIds(@RequestBody AuthDto token) {
    try {
      return new ResponseEntity<>(service.findUSCLD(token), HttpStatus.OK);
    } catch (Exception e) {
      throw new NoteNotFoundException(e.getMessage());
    }
  }

  @PostMapping("/new/{id}")
  public ResponseEntity<UserShoppingCartDto> newCarousel(@RequestBody UserShoppingCartEditDto noteCreateDto, @PathVariable("id") int id) {

    try {
      return new ResponseEntity<>(service.save(noteCreateDto, id), HttpStatus.OK);
    } catch (Exception e) {
      throw new NoteNotCreatedException(e.getMessage());
    }
  }

  @PostMapping("/del/{id}")
  public ResponseEntity<UserShoppingCartDto> delNote(@RequestBody UserShoppingCartEditDto token, @PathVariable("id") int id) {
    try {
      service.delete(token, id);
    } catch (Exception e) {
      throw new NoteNotDeletedException(e.getMessage());
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }

}