package com.epam.esm.exception;

public class RequestException extends RuntimeException {
    public RequestException(String message) {
        super(message);
    }
}
