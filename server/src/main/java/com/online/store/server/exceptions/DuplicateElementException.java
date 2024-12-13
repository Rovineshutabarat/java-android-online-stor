package com.online.store.server.exceptions;

public class DuplicateElementException extends IllegalArgumentException {
    public DuplicateElementException(String message) {
        super(message);
    }
}
