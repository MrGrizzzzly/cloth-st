package com.cloth.backend.exceptions.notCreated;


public class ModelNotCreatedException extends RuntimeException {
  public ModelNotCreatedException(String entityType , String msg) {
    super(entityType + " not created because: " + msg);
  }

}
