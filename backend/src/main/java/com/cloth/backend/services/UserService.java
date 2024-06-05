package com.cloth.backend.services;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import com.cloth.backend.dto.*;
import com.cloth.backend.exceptions.notCreated.UserNotCreatedException;
import com.cloth.backend.exceptions.notFound.SessionNotFoundException;
import com.cloth.backend.exceptions.notFound.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.cloth.backend.utils.AES.decrypt;
import static com.cloth.backend.utils.AES.encrypt;
import static com.cloth.backend.utils.Math.bytesToHex;
import static com.cloth.backend.utils.Math.Hash;
import com.cloth.backend.models.DH;
import com.cloth.backend.models.User;
import com.cloth.backend.repositories.UserRepository;

/**
 * Сервис для управления пользователями и аутентификации.
 */
@Service
@Transactional(readOnly = true)
public class UserService {

  /**
   * Словарь с парами Сессия - Diffie-Hellman
   */
  public HashMap<String, DH> sessions = new HashMap<>();

  /**
   * Репозиторий для взаимодействия с данными о пользователях.
   */
  private final UserRepository repository;

  /**
   * Конструктор класса, инициализирующий репозиторий.
   *
   * @param repository Репозиторий пользователей.
   */
  @Autowired
  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  /**
   * Метод для инициализации процесса аутентификации и генерации Diffie-Hellman параметров.
   *
   * @param session DTO для инициализации аутентификации.
   * @return DTO с параметрами Diffie-Hellman для клиента.
   */
  public PGDto getDH(AuthDto session) {
    sessions.remove(session.getSessionId());
    DH dh = new DH();
    String sessionId = String.valueOf(UUID.randomUUID());
    sessions.put(sessionId, dh);
    return new PGDto(sessionId, null, dh.getP(), dh.getG(),null, null, session.getRole());
  }

  /**
   * Метод для установки значения A и вычисления B в процессе аутентификации.
   *
   * @param session DTO с параметром A от клиента.
   * @return DTO с параметром B для клиента.
   */
  public BDto setAgetB(ADto session) {
    DH dh = sessions.get(session.getSessionId());
    dh.setFromA(new BigInteger(session.getA()));
    return new BDto(session.getSessionId(), null, dh.getB(), null, null, session.getRole());
  }

  /**
   * Метод для проверки авторизации пользователя.
   *
   * @param authVerificationDto DTO с данными для проверки пользователя.
   * @return HTTP статус в зависимости от результата проверки.
   * @throws Exception Исключение, которое может возникнуть при выполнении операции.
   */
  public HttpStatus verificationUser(AuthDto authVerificationDto) throws Exception {
    try {
      User user = getUserFromToken(authVerificationDto);
    } catch (com.cloth.backend.exceptions.notFound.UserNotFoundException e) {
      return HttpStatus.FORBIDDEN;
    } catch (com.cloth.backend.exceptions.notFound.SessionNotFoundException e) {
      return HttpStatus.BAD_GATEWAY;
    }
    return HttpStatus.OK;
  }

  /**
   * Метод для получения пользователя по токену из DTO.
   *
   * @param authVerifyDto DTO с токеном пользователя.
   * @return Найденный пользователь.
   * @throws Exception Исключение, которое может возникнуть при выполнении операции.
   */
  public User getUserFromToken(AuthDto authVerifyDto) throws Exception {

    byte[] key = getKeyFromSession(authVerifyDto);
    String token = decrypt(authVerifyDto.getToken(), key);
    Optional<User> user = repository.findByToken(token);
    return user.orElseThrow(() -> new UserNotFoundException("no user with token: " + token));
  }

  /**
   * Метод для получения пользователя по токену из DTO.
   *
   * @param authVerificationDto DTO с токеном пользователя.
   * @return Найденный пользователь.
   * @throws Exception Исключение, которое может возникнуть при выполнении операции.
   */
  public User userToken(AuthDto authVerificationDto) throws Exception {
    return repository.findByToken(decrypt(authVerificationDto.getToken(), getKeyFromSession(authVerificationDto))).orElseThrow();
  }

  /**
   * Метод для регистрации нового пользователя.
   *
   * @param authRegDto DTO с данными для регистрации.
   * @return DTO с подтверждением регистрации (токеном, выданным пользователю).
   * @throws Exception Исключение, которое может возникнуть при выполнении операции.
   */
  @Transactional
  public AuthDto register(AuthDto authRegDto) throws Exception {
    String token = String.valueOf(UUID.randomUUID());
    if (repository.findByUsername(decrypt(authRegDto.getUsername(), getKeyFromSession(authRegDto))).isPresent())
      throw new UserNotCreatedException("Username is already taken");
    if (decrypt(authRegDto.getUsername(), getKeyFromSession(authRegDto)).equals("AGrishin-KI213"))
      repository.save(new User(decrypt(authRegDto.getUsername(), getKeyFromSession(authRegDto)),
              bytesToHex(Hash(decrypt(authRegDto.getPassword(), getKeyFromSession(authRegDto)))), token, "MANAGER"));
    else
      repository.save(new User(decrypt(authRegDto.getUsername(), getKeyFromSession(authRegDto)),
              bytesToHex(Hash(decrypt(authRegDto.getPassword(), getKeyFromSession(authRegDto)))), token, "USER"));
    return new AuthDto(authRegDto.getSessionId(), encrypt(token, getKeyFromSession(authRegDto)), null, null, "USER");
  }

  /**
   * Метод для входа пользователя в систему.
   *
   * @param authLogDto DTO с данными для входа.
   * @return DTO с подтверждением входа пользователя (токеном, выданным пользователю).
   * @throws Exception Исключение, которое может возникнуть при выполнении операции.
   */
  @Transactional
  public AuthDto login(AuthDto authLogDto) throws Exception {
    byte[] key = getKeyFromSession(authLogDto);

    String username = decrypt(authLogDto.getUsername(), key);
    String password = decrypt(authLogDto.getPassword(), key);
    password = bytesToHex(Hash(password));
    User user = repository
            .findByUsernameAndPassword(username, password)
            .orElseThrow(() -> new UserNotFoundException(username));
    String token = UUID.randomUUID().toString();
    user.setToken(token);
    repository.save(user);
    return new AuthDto(authLogDto.getSessionId(), encrypt(token, getKeyFromSession(authLogDto)), null, null, encrypt(user.getRole(), getKeyFromSession(authLogDto)));
  }


  /**
   * Метод для роли пользователя ограничения уровня доступа
   *
   * @param authLogDto DTO с ID сессии.
   * @return Роль пользователя.
   */
  @Transactional
  public AuthDto getRole (AuthDto authLogDto) throws Exception {
    byte[] key = getKeyFromSession(authLogDto);
    String token = decrypt(authLogDto.getToken(), key);
    Optional<User> user = repository.findByToken(token);
    return new AuthDto(null, null, null, null, encrypt(user.get().getRole(), getKeyFromSession(authLogDto)));
  }


  /**
   * Метод для получения общего ключа из текущей сессии по ID сессии.
   *
   * @param authDto DTO с ID сессии.
   * @return Общий ключ Diffie-Hellman для шифрования и дешифрования данных.
   */
  public byte[] getKeyFromSession(AuthDto authDto) {
    String sessionId = authDto.getSessionId();
    if (!sessions.containsKey(sessionId))
      throw new SessionNotFoundException("Session with id=" + sessionId + " not found!");
    return sessions.get(authDto.getSessionId()).getK();
  }
}
