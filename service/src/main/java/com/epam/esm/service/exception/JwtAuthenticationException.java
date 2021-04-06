package com.epam.esm.service.exception;


public class JwtAuthenticationException extends RuntimeException {

    public JwtAuthenticationException() {
        super();
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }


}
