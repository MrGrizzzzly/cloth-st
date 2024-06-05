package com.cloth.backend.utils;

import java.util.Base64;
import java.util.Objects;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.cloth.backend.dto.CarouselNoteDto;
import com.cloth.backend.dto.UserShoppingCartDto;
import com.cloth.backend.models.CarouselNotes;
import com.cloth.backend.models.UserShoppingCart;
import org.springframework.stereotype.Component;
import com.cloth.backend.dto.NoteDto;
import com.cloth.backend.models.Notes;

/**
 * Класс, представляющий реализацию шифрования и дешифрования методом AES.
 */
@Component
public class AES {

  /**
   * Метод для шифрования сообщения с использованием ключа.
   *
   * @param message   Сообщение, которое требуется зашифровать.
   * @param keyBytes  Ключ для шифрования.
   * @return Зашифрованное сообщение в формате Base64.
   * @throws Exception Исключение, которое может возникнуть в процессе шифрования.
   */
  public static String encrypt(String message, byte[] keyBytes) throws Exception {
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(keyBytes, "AES"));
    return Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes()));
  }

  /**
   * Метод для расшифровки зашифрованного сообщения с использованием ключа.
   *
   * @param encryptedMessage Зашифрованное сообщение в формате Base64.
   * @param keyBytes         Ключ для расшифровки.
   * @return Расшифрованное сообщение.
   * @throws Exception Исключение, которое может возникнуть в процессе расшифровки.
   */
  public static String decrypt(String encryptedMessage, byte[] keyBytes) throws Exception {
    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(keyBytes, "AES"));
    return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedMessage)));
  }

  /**
   * Метод для шифрования джинсов в объект DTO.
   *
   * @param Note Джинсы для шифрования.
   * @param key  Общий ключ для шифрования данных.
   * @return DTO с зашифрованными данными джинсов.
   * @throws Exception Исключение, которое может возникнуть при выполнении операции шифрования.
   */
  public static NoteDto encryptNote(Notes Note, byte[] key) throws Exception {
    NoteDto NoteDto = new NoteDto();
    NoteDto.setId(encrypt(String.valueOf(Note.getId()), key));
    NoteDto.setName_note(encrypt(Note.getName_note(), key));
    NoteDto.setNote(encrypt(Note.getNote(), key));
    NoteDto.setPicture(encrypt(Note.getPicture(), key));
    NoteDto.setComposition(encrypt(Note.getComposition(), key));
    NoteDto.setColor(encrypt(Note.getColor(), key));
    NoteDto.setSeason(encrypt(Note.getSeason(), key));
    NoteDto.setInsulation(encrypt(Note.getInsulation(), key));
    NoteDto.setType_of_landing(encrypt(Note.getType_of_landing(), key));
    NoteDto.setCountry_of_manufacture(encrypt(Note.getCountry_of_manufacture(), key));
    NoteDto.setType_of_fastener(encrypt(Note.getType_of_fastener(), key));
    NoteDto.setType_of_pockets(encrypt(Note.getType_of_pockets(), key));
    NoteDto.setEquipment(encrypt(Note.getEquipment(), key));
    return NoteDto;
  }

  /**
   * Метод для дешифрования объекта DTO с джинсами.
   *
   * @param NoteDto DTO с зашифрованными данными джинсов.
   * @param key     Общий ключ для дешифрования данных.
   * @return Джинсы с дешифрованными данными.
   * @throws Exception Исключение, которое может возникнуть при выполнении операции дешифрования.
   */
  public static Notes decryptNote(NoteDto NoteDto, byte[] key) throws Exception {
    Notes Note = new Notes();
    if (!Objects.equals(NoteDto.getId(), "null"))
      Note.setId(Integer.parseInt(decrypt(NoteDto.getId(), key)));
    Note.setName_note(decrypt(NoteDto.getName_note(), key));
    Note.setNote(decrypt(NoteDto.getNote(), key));
    Note.setPicture(decrypt(NoteDto.getPicture(), key));
    Note.setComposition(decrypt(NoteDto.getComposition(), key));
    Note.setColor(decrypt(NoteDto.getColor(), key));
    Note.setSeason(decrypt(NoteDto.getSeason(), key));
    Note.setInsulation(decrypt(NoteDto.getInsulation(), key));
    Note.setType_of_landing(decrypt(NoteDto.getType_of_landing(), key));
    Note.setCountry_of_manufacture(decrypt(NoteDto.getCountry_of_manufacture(), key));
    Note.setType_of_fastener(decrypt(NoteDto.getType_of_fastener(), key));
    Note.setType_of_pockets(decrypt(NoteDto.getType_of_pockets(), key));
    Note.setEquipment(decrypt(NoteDto.getEquipment(), key));
    return Note;
  }


  /**
   * Метод для шифрования карусели-картинок джинсов в объект DTO.
   *
   * @param CarouselNote Карусель-картинок для шифрования.
   * @param key  Общий ключ для шифрования данных.
   * @return DTO с зашифрованными данными джинсов.
   * @throws Exception Исключение, которое может возникнуть при выполнении операции шифрования.
   */
  public static CarouselNoteDto encryptCarouselNote(CarouselNotes CarouselNote, byte[] key) throws Exception {
    CarouselNoteDto CarouselNoteDto = new CarouselNoteDto();
    CarouselNoteDto.setId(encrypt(String.valueOf(CarouselNote.getId()), key));
    CarouselNoteDto.setPicture(encrypt(CarouselNote.getPicture(), key));
    return CarouselNoteDto;
  }

  /**
   * Метод для дешифрования объекта DTO с каруселью-картинок.
   *
   * @param CarouselNDto DTO с зашифрованными данными карусели.
   * @param key          Общий ключ для дешифрования данных.
   * @return Карусель с дешифрованными данными.
   * @throws Exception Исключение, которое может возникнуть при выполнении операции дешифрования.
   */
  public static CarouselNotes decryptCarouselNote(CarouselNoteDto CarouselNDto, byte[] key) throws Exception {
    CarouselNotes CarouselNote = new CarouselNotes();
    if (!Objects.equals(CarouselNDto.getId(), "null"))
      CarouselNote.setId(Integer.parseInt(decrypt(CarouselNDto.getId(), key)));
    CarouselNote.setPicture(decrypt(CarouselNDto.getPicture(), key));
    return CarouselNote;
  }


  /**
   * Метод для шифрования карусели-картинок джинсов в объект DTO.
   *
   * @param CarouselNote Карусель-картинок для шифрования.
   * @param key  Общий ключ для шифрования данных.
   * @return DTO с зашифрованными данными джинсов.
   * @throws Exception Исключение, которое может возникнуть при выполнении операции шифрования.
   */
  public static UserShoppingCartDto encryptUserShoppingCart(UserShoppingCart CarouselNote, byte[] key) throws Exception {
    UserShoppingCartDto UserShoppingCartD = new UserShoppingCartDto();
    UserShoppingCartD.setId(encrypt(String.valueOf(CarouselNote.getId()), key));
    return UserShoppingCartD;
  }

  /**
   * Метод для дешифрования объекта DTO с каруселью-картинок.
   *
   * @param CarouselNDto DTO с зашифрованными данными карусели.
   * @param key          Общий ключ для дешифрования данных.
   * @return Карусель с дешифрованными данными.
   * @throws Exception Исключение, которое может возникнуть при выполнении операции дешифрования.
   */
  public static UserShoppingCart decryptUserShoppingCart(UserShoppingCartDto CarouselNDto, byte[] key) throws Exception {
    UserShoppingCart CarouselNote = new UserShoppingCart();
    if (!Objects.equals(CarouselNDto.getId(), "null"))
      CarouselNote.setId(Integer.parseInt(decrypt(CarouselNDto.getId(), key)));
    return CarouselNote;
  }
}


