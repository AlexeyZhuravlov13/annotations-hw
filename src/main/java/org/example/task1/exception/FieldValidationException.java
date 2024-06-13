package org.example.task1.exception;

public class FieldValidationException extends RuntimeException{
    public FieldValidationException(String message) {
        super(message);
    }
}
