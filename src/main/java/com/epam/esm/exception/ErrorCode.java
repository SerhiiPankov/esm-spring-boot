package com.epam.esm.exception;

public enum ErrorCode {
    REFLECTION("001"),
    DATA_PROCESSING("002"),
    REQUEST("003");

    private final String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
