package com.cloth.backend.exceptions.notUpdated;

public class NoteNotUpdatedException extends ModelNotUpdatedException {

    public static final String entityType = "Note";

    public NoteNotUpdatedException(String msg) {
        super(entityType, msg);
    }
}