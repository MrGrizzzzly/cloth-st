package com.cloth.backend.exceptions.notDeleted;

public class NoteNotDeletedException extends ModelNotDeletedException {

    public static final String entityType = "Note";

    public NoteNotDeletedException(String msg) {
        super(entityType, msg);
    }
}