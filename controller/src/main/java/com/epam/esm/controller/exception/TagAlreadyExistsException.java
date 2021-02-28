package com.epam.esm.controller.exception;



public class TagAlreadyExistsException extends RuntimeException {
    private String code;

    public TagAlreadyExistsException(CustomErrorCode code) {
        this.code = code.getCode();
    }

    public TagAlreadyExistsException(String message, CustomErrorCode code) {
        super(message);
        this.code = code.getCode();
    }

    public String getCode() {
        return code;
    }
}
