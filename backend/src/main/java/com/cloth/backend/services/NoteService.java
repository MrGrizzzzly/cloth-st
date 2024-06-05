package com.cloth.backend.services;

import java.util.List;

import com.cloth.backend.dto.*;
import com.cloth.backend.exceptions.notFound.NoteNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cloth.backend.models.Notes;
import com.cloth.backend.repositories.NoteRepository;
import com.cloth.backend.utils.AES;

import static com.cloth.backend.utils.AES.decrypt;

/**
 * Сервис для обработки операций с джинсами.
 */
@Service
@Transactional(readOnly = true)
public class NoteService {

  /**
   * Репозиторий для взаимодействия с данными о джинсах.
   */
  private final NoteRepository repository;
  /**
   * Сервис для работы с пользователями.
   */
  private final UserService userService;
  /**
   * Объект для отображения данных между сущностями и DTO.
   */
  private final ModelMapper modelMapper;

  /**
   * Репозиторий для взаимодействия с данными карусели джинсов.
   */
  private final CarouselNoteService carouselNoteService;

  /**
   * Конструктор класса, инициализирующий необходимые зависимости.
   *
   * @param repository   Репозиторий джинсов.
   * @param userService  Сервис пользователей.
   * @param modelMapper  Объект для отображения данных между сущностями и DTO.
   * @param carouselNoteService Репозиторий карусели джинсов.
   */
  @Autowired
  public NoteService(NoteRepository repository, UserService userService,
                     ModelMapper modelMapper, CarouselNoteService carouselNoteService) {
    this.repository = repository;
    this.userService = userService;
    this.modelMapper = modelMapper;
    this.carouselNoteService = carouselNoteService;
  }


  /**
   * Метод для поиска джинсов, принадлежащих пользователю.
   *
   * @param token DTO для аутентификации пользователя.
   * @return DTO с информацией о джинсах пользователя.
   * @throws Exception Исключение, которое может возникнуть при выполнении операции.
   */
  @Transactional
  public NoteListDto findByUser(AuthDto token) throws Exception {
    List<Notes> notes = repository.findByUser(userService.userToken(token));
    if (notes.isEmpty())
      return mapNotes(repository.findByUser(userService.userToken(token)), userService.getKeyFromSession(token));
    return mapNotes(notes, userService.getKeyFromSession(token));
  }

  /**
   * Метод для поиска джинсов.
   *
   * @param token DTO для аутентификации пользователя.
   * @return DTO с информацией о джинсах пользователя.
   */
  @Transactional
  public NoteListDto findByAll(AuthDto token) {
    List<Notes> notes = repository.findAll();
    if (notes.isEmpty())
      return mapNotes(repository.findAll(), userService.getKeyFromSession(token));
    return mapNotes(notes, userService.getKeyFromSession(token));
  }

  /**
   * Метод для сохранения новых джинсов.
   *
   * @param noteCreateDto DTO с информацией о создаваемых джинсах.
   * @return DTO с информацией о сохраненных джинсах.
   * @throws Exception Исключение, которое может возникнуть при выполнении операции.
   */
  @Transactional
  public NoteDto save(NoteEditDto noteCreateDto) throws Exception {
    NoteDto noteDto = modelMapper.map(noteCreateDto, NoteDto.class);
    noteDto.setId("null");
    Notes newNotes = AES.decryptNote(noteDto, userService.getKeyFromSession(noteCreateDto));
    newNotes.setUser(userService.userToken(noteCreateDto));
    repository.save(newNotes);
    return AES.encryptNote(newNotes, userService.getKeyFromSession(noteCreateDto));
  }


  /**
   * Метод для обновления информации о существующих джинсах.
   *
   * @param noteEditDto DTO с информацией об изменяемых джинсах.
   * @throws Exception Исключение, которое может возникнуть при выполнении операции.
   */
  @Transactional
  public NoteDto update(NoteEditDto noteEditDto) throws Exception {
    Notes edited = AES.decryptNote(modelMapper.map(noteEditDto, NoteDto.class),
            userService.getKeyFromSession(noteEditDto));
    if (!repository.existsById(edited.getId()))
      throw new NoteNotFoundException(edited.getId());
    edited.setUser(userService.userToken(noteEditDto));
    repository.save(edited);
    return AES.encryptNote(edited, userService.getKeyFromSession(noteEditDto));
  }

  /**
   * Метод для удаления джинсов по его идентификатору.
   *
   * @param token DTO с информацией об удаляемых джинсах.
   * @throws Exception Исключение, которое может возникнуть при выполнении операции.
   */
  @Transactional
  public void delete(NoteEditDto token) throws Exception {

    byte[] key = userService.getKeyFromSession(token);
    Integer id = Integer.parseInt(decrypt(token.getId(), key));
    carouselNoteService.delete(id);
    if (repository.existsById(id))
      repository.deleteById(id);
    else
      throw new NoteNotFoundException(id);

  }

  /**
   * Метод для отображения списка джинсов с использованием общего ключа.
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

}
