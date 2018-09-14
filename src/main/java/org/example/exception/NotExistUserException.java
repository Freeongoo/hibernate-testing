package org.example.exception;

public class NotExistUserException extends Exception {
    public NotExistUserException(String message) {
        super(message);
    }
}
