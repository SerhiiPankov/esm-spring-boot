package com.epam.esm.exception;

public enum ErrorCode {
    REFLECTION("001"),
    DATA_PROCESSING("002"),
    REQUEST("003"),
    USER_NOT_FOUND("004"),
    AUTHENTICATION("005"),
    FORBIDDEN("006");

    private final String errorCode;

    ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
