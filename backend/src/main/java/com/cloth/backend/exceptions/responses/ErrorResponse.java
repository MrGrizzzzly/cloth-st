package com.cloth.backend.exceptions.responses;

import java.sql.Timestamp;
import java.util.Objects;

public class ErrorResponse {

  private String message;

  private Timestamp timestamp;

  public ErrorResponse() {
    this.message = "";
    this.timestamp = new Timestamp(System.currentTimeMillis());
  }

  public ErrorResponse(String message) {
    this.message = message;
    this.timestamp = new Timestamp(System.currentTimeMillis());
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ErrorResponse that = (ErrorResponse) o;

    if (!Objects.equals(message, that.message)) return false;
    return Objects.equals(timestamp, that.timestamp);
  }

  @Override
  public int hashCode() {
    int result = message != null ? message.hashCode() : 0;
    result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
    return result;
  }
}
