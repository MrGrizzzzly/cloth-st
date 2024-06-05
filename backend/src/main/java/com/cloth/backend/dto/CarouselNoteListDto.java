package com.cloth.backend.dto;

import java.util.List;
import java.util.Objects;

public class CarouselNoteListDto {

  List<CarouselNoteDto> carouselNotesList;

  public CarouselNoteListDto(List<CarouselNoteDto> carouselNotesList) {
    this.carouselNotesList = carouselNotesList;
  }

  public List<CarouselNoteDto> getCarouselNotesList() {
    return carouselNotesList;
  }

  public void setCarouselNotesList(List<CarouselNoteDto> carouselNotesList) {
    this.carouselNotesList = carouselNotesList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    CarouselNoteListDto that = (CarouselNoteListDto) o;

    return Objects.equals(carouselNotesList, that.carouselNotesList);
  }

  @Override
  public int hashCode() {
    return carouselNotesList != null ? carouselNotesList.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "CarouselNoteListDto{" +
            "carouselNotesList=" + carouselNotesList +
            '}';
  }
}
