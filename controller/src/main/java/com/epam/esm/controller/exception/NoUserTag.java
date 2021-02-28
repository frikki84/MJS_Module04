package com.epam.esm.controller.exception;


public class NoUserTag  extends RuntimeException {
    private String code;

    public NoUserTag(CustomErrorCode code) {
        this.code = code.getCode();
    }

    public NoUserTag(String message, CustomErrorCode code) {
        super(message);
        this.code = code.getCode();
    }

    public String getCode() {
        return code;
    }
}
