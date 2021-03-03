package com.epam.esm.service.exception.notused;


import com.epam.esm.service.exception.CustomErrorCode;

public class NoCertificatesWithName extends RuntimeException {
    private String code;

    public NoCertificatesWithName(CustomErrorCode code) {
        this.code = code.getCode();
    }

    public NoCertificatesWithName(String message, CustomErrorCode code) {
        super(message);
        this.code = code.getCode();
    }

    public String getCode() {
        return code;
    }
}