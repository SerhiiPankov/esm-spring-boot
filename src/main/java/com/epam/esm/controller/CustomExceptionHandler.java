package com.epam.esm.controller;

import com.epam.esm.exception.DataProcessingException;
import com.epam.esm.exception.ErrorCode;
import com.epam.esm.exception.RequestException;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = RequestException.class)
    protected ResponseEntity<Object> handleRequestException(RequestException ex,
                                                    WebRequest request) {
        return handleExceptionInternal(ex,
                getBody(HttpStatus.BAD_REQUEST, ex, ErrorCode.REQUEST),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = DataProcessingException.class)
    protected ResponseEntity<Object> handleDataProcessingException(DataProcessingException ex,
                                                    WebRequest request) {
        return handleExceptionInternal(ex,
                getBody(HttpStatus.CONFLICT, ex, ErrorCode.DATA_PROCESSING),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex,
                                                  WebRequest request) {
        System.out.println(Arrays.toString(ex.getStackTrace()));
        return handleExceptionInternal(ex,
                getBody(HttpStatus.INTERNAL_SERVER_ERROR, ex, ErrorCode.DATA_PROCESSING),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    private Map<String, Object> getBody(HttpStatus httpStatus, Exception ex, ErrorCode errorCode) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", httpStatus);
        body.put("errorMessage", ex.getMessage());
        body.put("errorCode", errorCode.getErrorCode());
        return body;
    }
}
