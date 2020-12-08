package org.example.exceptions;

public class UnknownLanguageException extends Exception {
    public UnknownLanguageException() {
    }

    public UnknownLanguageException(String message) {
        super(message);
    }
}
