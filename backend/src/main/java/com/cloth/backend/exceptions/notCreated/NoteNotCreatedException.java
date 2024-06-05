package com.cloth.backend.exceptions.notCreated;

public class NoteNotCreatedException extends ModelNotCreatedException {

    public static final String entityType = "Note";

    public NoteNotCreatedException(String msg) {
        super(entityType, msg);
    }
}
