package com.cloth.backend.exceptions.notFound;

public class NoteNotFoundException extends ModelNotFoundException {

    public static final String entityType = "Note";

    public NoteNotFoundException(String msg) {
        super(entityType, msg);
    }

    public NoteNotFoundException(int id) {
        super(entityType, id);
    }

}
