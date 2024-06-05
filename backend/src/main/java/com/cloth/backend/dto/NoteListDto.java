package com.cloth.backend.dto;

import java.util.List;
import java.util.Objects;

public class NoteListDto {

  List<NoteDto> notesList;

  public NoteListDto(List<NoteDto> notesList) {
    this.notesList = notesList;
  }

  public List<NoteDto> getNotesList() {
    return notesList;
  }

  public void setNotesList(List<NoteDto> notesList) {
    this.notesList = notesList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    NoteListDto that = (NoteListDto) o;

    return Objects.equals(notesList, that.notesList);
  }

  @Override
  public int hashCode() {
    return notesList != null ? notesList.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "NoteListDto{" +
            "notesList=" + notesList +
            '}';
  }
}
