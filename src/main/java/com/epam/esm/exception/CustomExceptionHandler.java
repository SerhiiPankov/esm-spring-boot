package com.epam.esm.exception;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @ExceptionHandler(value = AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex,
                                                                   WebRequest request) {
        return handleExceptionInternal(ex,
                getBody(HttpStatus.FORBIDDEN, ex, ErrorCode.DATA_PROCESSING),
                new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(value = DataProcessingException.class)
    protected ResponseEntity<Object> handleDataProcessingException(DataProcessingException ex,
                                                    WebRequest request) {
        return handleExceptionInternal(ex,
                getBody(HttpStatus.CONFLICT, ex, ErrorCode.DATA_PROCESSING),
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex,
                                                                  WebRequest request) {
        return handleExceptionInternal(ex,
                getBody(HttpStatus.NOT_FOUND, ex, ErrorCode.USER_NOT_FOUND),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex,
                                                                  WebRequest request) {
        return handleExceptionInternal(ex,
                getBody(HttpStatus.UNAUTHORIZED, ex, ErrorCode.AUTHENTICATION),
                new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleAllException(Exception ex,
                                                  WebRequest request) {
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
