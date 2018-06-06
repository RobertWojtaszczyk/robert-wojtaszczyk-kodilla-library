package com.rw.library.errors;

public class EntityConstraintViolationException extends RuntimeException {
    public EntityConstraintViolationException(String message) {
        super(message);
    }
}
