package com.cloth.backend.controllers;

import com.cloth.backend.exceptions.notCreated.UserNotCreatedException;
import com.cloth.backend.exceptions.notDeleted.UserNotDeletedException;
import com.cloth.backend.exceptions.notFound.UserNotFoundException;
import com.cloth.backend.exceptions.notUpdated.UserNotUpdatedException;
import com.cloth.backend.utils.CrudErrorHandlers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cloth.backend.dto.ADto;
import com.cloth.backend.dto.AuthDto;
import com.cloth.backend.dto.BDto;
import com.cloth.backend.dto.PGDto;
import com.cloth.backend.services.UserService;

/**
 * Контроллер для аутентификации и авторизации пользователей.
 */
@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/auth")
public class AuthController extends CrudErrorHandlers<
        UserNotCreatedException,
        UserNotFoundException,
        UserNotUpdatedException,
        UserNotDeletedException
        >{

  /**
   * Сервис для обработки запросов аутентификации и авторизации пользователей.
   */
  private final UserService service;


  /**
   * Конструктор контроллера.
   *
   * @param service Сервис для обработки запросов аутентификации и авторизации пользователей.
   */
  @Autowired
  public AuthController(UserService service) {
    this.service = service;
  }

  /**
   * Обработчик запроса на получение Diffie-Hellman сессии.
   *
   * @param session Объект сессии для аутентификации.
   * @return ResponseEntity с объектом AuthInitDto и статусом OK.
   */
  @PostMapping("/dh")
  public ResponseEntity<PGDto> getSession(@RequestBody AuthDto session) {
   return new ResponseEntity<>(service.getDH(session), HttpStatus.OK);
  }

  /**
   * Обработчик запроса на получение открытого ключа B и завершение Diffie-Hellman обмена.
   *
   * @param A Объект с данными A для Diffie-Hellman обмена.
   * @return ResponseEntity с объектом AuthFinRespDto и статусом OK.
   */
  @PostMapping("/key")
  public ResponseEntity<BDto> getBKey(@RequestBody ADto A) {
    return new ResponseEntity<>(service.setAgetB(A), HttpStatus.OK);
  }

  /**
   * Обработчик запроса на верификацию пользователя.
   *
   * @param verifyDto Объект с данными для верификации пользователя.
   * @return ResponseEntity с HttpStatus в зависимости от результата верификации.
   * @throws Exception Исключение, которое может возникнуть при выполнении верификации.
   */
  @PostMapping("/verification")
  public ResponseEntity<HttpStatus> verificationUser(@RequestBody AuthDto verifyDto) throws Exception {
    return new ResponseEntity<>(service.verificationUser(verifyDto));
  }

  /**
   * Обработчик запроса на регистрацию нового пользователя.
   *
   * @param authRegDto Объект с данными для регистрации нового пользователя.
   * @return ResponseEntity с объектом AuthVerifyDto и статусом OK.
   * @throws UserNotCreatedException Исключение, которое может возникнуть при неудачной регистрации.
   */
  @PostMapping("/register")
  public ResponseEntity<AuthDto> register(@RequestBody AuthDto authRegDto) {
    try {
      AuthDto token = service.register(authRegDto);
      return new ResponseEntity<>(token, HttpStatus.OK);
    } catch (Exception e) {
      throw new UserNotCreatedException(e.getMessage());
    }
  }

  /**
   * Обработчик запроса на вход пользователя в систему.
   *
   * @param authLogDto Объект с данными для входа пользователя.
   * @return ResponseEntity с объектом AuthVerifyDto и статусом OK.
   * @throws Exception Исключение, которое может возникнуть при выполнении входа пользователя.
   */
  @PostMapping("/login")
  public ResponseEntity<AuthDto> login(@RequestBody AuthDto authLogDto) throws Exception {
    return new ResponseEntity<>(service.login(authLogDto), HttpStatus.OK);
  }

  /**
   * Обработчик запроса на роль пользователя в системе.
   *
   * @param authLogDto Объект с данными для входа пользователя.
   * @return ResponseEntity с объектом AuthVerifyDto и статусом OK.
   * @throws Exception Исключение, которое может возникнуть при выполнении.
   */
  @PostMapping("/getRole")
  public ResponseEntity<AuthDto> role(@RequestBody AuthDto authLogDto) throws Exception {
    return new ResponseEntity<>(service.getRole(authLogDto), HttpStatus.OK);
  }
}
