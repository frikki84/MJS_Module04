package com.epam.esm.controller.exception;

public class MyResourceNotFoundException extends RuntimeException{
    private String code;

    public MyResourceNotFoundException(CustomErrorCode code) {
        this.code = code.getCode();
    }

    public MyResourceNotFoundException(String message, CustomErrorCode code) {
        super(message);
        this.code = code.getCode();
    }

    public String getCode() {
        return code;
    }
}
