package com.cloth.backend.services;

import com.cloth.backend.dto.*;
import com.cloth.backend.models.CarouselNotes;
import com.cloth.backend.repositories.CarouselNoteRepository;
import com.cloth.backend.repositories.NoteRepository;
import com.cloth.backend.utils.AES;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис для обработки операций с каруселью джинсов.
 */
@Service
@Transactional(readOnly = true)
public class CarouselNoteService {

  /**
   * Репозиторий для взаимодействия с данными карусели джинсов.
   */
  private final CarouselNoteRepository repository;

  /**
   * Сервис для работы с пользователями.
   */
  private final UserService userService;

  /**
   * Объект для отображения данных между сущностями и DTO.
   */
  private final ModelMapper modelMapper;

  private final NoteRepository repository2;

  /**
   * Конструктор класса, инициализирующий необходимые зависимости.
   *
   * @param repository  Репозиторий карусели джинсов.
   * @param userService Сервис пользователей.
   * @param modelMapper Объект для отображения данных между сущностями и DTO.
   * @param repository2
   */
  @Autowired
  public CarouselNoteService(CarouselNoteRepository repository, UserService userService,
                             ModelMapper modelMapper, NoteRepository repository2) {
    this.repository = repository;
    this.userService = userService;
    this.modelMapper = modelMapper;
    this.repository2 = repository2;
  }

  /**
   * Метод для поиска джинсов, принадлежащих товару.
   *
   * @param token DTO для аутентификации пользователя.
   * @param id DTO джинсов.
   * @return DTO с информацией о джинсах пользователя.
   */
  @Transactional
  public CarouselNoteListDto findById(AuthDto token, int id) {
    List<CarouselNotes> notes = repository.findAll().stream().filter(obj -> obj.getNote().getId() == id).toList();
    if (notes.isEmpty())
      return mapNotes(repository.findAll().stream().filter(obj -> obj.getNote().getId() == id).toList(),
              userService.getKeyFromSession(token));
    return mapNotes(notes, userService.getKeyFromSession(token));
  }

  /**
   * Метод для сохранения новых джинсов.
   *
   * @param noteCreateDto DTO с информацией о создаваемых джинсах.
   * @return DTO с информацией о сохраненных джинсах.
   * @throws Exception Исключение, которое может возникнуть при выполнении операции. AuthDto token, Integer id
   */
  @Transactional
  public CarouselNoteDto save(CarouselNoteEditDto noteCreateDto, int id) throws Exception {
    CarouselNoteDto noteDto = modelMapper.map(noteCreateDto, CarouselNoteDto.class);
    noteDto.setId("null");
    CarouselNotes newNotes = AES.decryptCarouselNote(noteDto, userService.getKeyFromSession(noteCreateDto));
    newNotes.setNote(repository2.findById(id).orElse(null));
    repository.save(newNotes);
    return AES.encryptCarouselNote(newNotes, userService.getKeyFromSession(noteCreateDto));
  }



  /**
   * Метод для удаления карусели джинсов по идентификатору товара.
   *
   * @param id DTO с информацией об удаляемой карусели.
   */
  public void delete(Integer id) {
    List<CarouselNotes> notes = repository.findAll().stream().filter(obj -> obj.getNote().getId() == id).toList();
    for (CarouselNotes note : notes)
      repository.deleteById(note.getId());
  }

  /**
   * Метод для отображения списка карусели джинсов с использованием общего ключа.
   *
   * @param notes Список джинсов.
   * @param key   Общий ключ для шифрования и дешифрования данных.
   * @return DTO с информацией о джинсах.
   * @throws RuntimeException Исключение, которое может возникнуть при выполнении операции.
   */
  private CarouselNoteListDto mapNotes(List<CarouselNotes> notes, byte[] key) throws RuntimeException {
    return new CarouselNoteListDto(notes.stream().map(note -> {
      try {
        return AES.encryptCarouselNote(note, key);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }).toList());
  }


}
