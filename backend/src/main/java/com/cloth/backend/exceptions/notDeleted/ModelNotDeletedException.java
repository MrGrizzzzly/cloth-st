package com.cloth.backend.exceptions.notDeleted;

public class ModelNotDeletedException extends RuntimeException {
  public ModelNotDeletedException(String entityType , String msg) {
    super(entityType + " not deleted because: " + msg);
  }

}
