package com.cloth.backend.services;

import com.cloth.backend.dto.*;
import com.cloth.backend.models.*;
import com.cloth.backend.repositories.NoteRepository;
import com.cloth.backend.repositories.UserShoppingCartRepository;
import com.cloth.backend.utils.AES;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Сервис для управления пользователями и аутентификации.
 */
@Service
@Transactional(readOnly = true)
public class UserShoppingCartService {

  /**
   * Сервис для работы с пользователями.
   */
  private final UserService userService;

  /**
   * Объект для отображения данных между сущностями и DTO.
   */
  private final ModelMapper modelMapper;

  private final NoteRepository repository2;

  private final UserShoppingCartRepository repository3;

  /**
   * Конструктор класса, инициализирующий необходимые зависимости.
   *
   * @param userService Сервис пользователей.
   * @param modelMapper Объект для отображения данных между сущностями и DTO.
   * @param repository2
   */
  @Autowired
  public UserShoppingCartService( UserService userService, ModelMapper modelMapper, NoteRepository repository2,
                                  UserShoppingCartRepository repository3) {
    this.userService = userService;
    this.modelMapper = modelMapper;
    this.repository2 = repository2;
    this.repository3 = repository3;
  }

  /**
   * Метод для поиска джинсов, принадлежащих товару.
   *
   * @param token DTO для аутентификации пользователя.
   * @return DTO с информацией о джинсах пользователя.
   */
  @Transactional
  public NoteListDto findByUser(AuthDto token) throws Exception {
    List<Notes> notes = repository3.findByUser(userService.userToken(token)).stream().map(UserShoppingCart::getNote).toList();

    if (notes.isEmpty())
      return mapNotes(repository3.findByUser(userService.userToken(token)).stream().map(UserShoppingCart::getNote).toList(),
              userService.getKeyFromSession(token));
    return mapNotes(notes, userService.getKeyFromSession(token));
  }

  @Transactional
  public UserShoppingCartListDto findUSCLD(AuthDto token) throws Exception {
    List<UserShoppingCart> notes = repository3.findByUser(userService.userToken(token));
    if (notes.isEmpty())
      return mapNotes2(repository3.findByUser(userService.userToken(token)), userService.getKeyFromSession(token));
    return mapNotes2(notes, userService.getKeyFromSession(token));
  }

  /**
   * Метод для сохранения новых джинсов.
   *
   * @param noteCreateDto DTO с информацией о создаваемых джинсах.
   * @return DTO с информацией о сохраненных джинсах.
   * @throws Exception Исключение, которое может возникнуть при выполнении операции. AuthDto token, Integer id
   */
  @Transactional
  public UserShoppingCartDto save(UserShoppingCartEditDto noteCreateDto, int id) throws Exception {
    UserShoppingCartDto noteDto = modelMapper.map(noteCreateDto, UserShoppingCartDto.class);
    noteDto.setId("null");
    UserShoppingCart newNotes = AES.decryptUserShoppingCart(noteDto, userService.getKeyFromSession(noteCreateDto));
    newNotes.setNote(repository2.findById(id).orElse(null));
    newNotes.setUser(userService.userToken(noteCreateDto));
    try {
      repository3.findById(
              Objects.requireNonNull(repository3.findByUser(userService.userToken(noteCreateDto)).stream()
              .filter(obj -> obj.getNote().getId() == id).findAny().orElse(null)).getId());
    }catch (Exception e){
      repository3.save(newNotes);
    }
    return AES.encryptUserShoppingCart(newNotes, userService.getKeyFromSession(noteCreateDto));
  }

  /**
   * Метод для удаления карусели джинсов по идентификатору товара.
   *
   * @param id DTO с информацией об удаляемой карусели.
   */
  @Transactional
  public void delete(AuthDto token, Integer id) throws Exception {
    repository3.deleteById(Objects.requireNonNull(repository3.findByUser(userService.userToken(token)).stream()
          .filter(obj -> obj.getNote().getId() == id).findAny().orElse(null)).getId());
  }

  /**
   * Метод для отображения списка карусели джинсов с использованием общего ключа.
   *
   * @param notes Список джинсов.
   * @param key   Общий ключ для шифрования и дешифрования данных.
   * @return DTO с информацией о джинсах.
   * @throws RuntimeException Исключение, которое может возникнуть при выполнении операции.
   */
  private NoteListDto mapNotes(List<Notes> notes, byte[] key) throws RuntimeException {
    return new NoteListDto(notes.stream().map(note -> {
      try {
        return AES.encryptNote(note, key);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }).toList());
  }

  private UserShoppingCartListDto mapNotes2(List<UserShoppingCart> notes, byte[] key) throws RuntimeException {
    return new UserShoppingCartListDto(notes.stream().map(note -> {
      try {
        return AES.encryptUserShoppingCart(note, key);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }).toList());
  }


}
