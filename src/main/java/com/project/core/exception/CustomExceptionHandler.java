package com.project.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Farhad Enayati
 * @version 1.0
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ApplicationExceptions.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionEntity handleApplicationException(ApplicationExceptions ex) {
        return ex.getExceptionEntity();
    }

}