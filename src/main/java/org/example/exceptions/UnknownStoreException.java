package org.example.exceptions;

public class UnknownStoreException extends Exception {

    public UnknownStoreException() {
    }

    public UnknownStoreException(String message) {
        super(message);
    }
}
