package com.epam.esm.service.exception;

public class AccessException extends RuntimeException{

    public AccessException() {
    }

    public AccessException(String message) {
        super(message);
    }
}
