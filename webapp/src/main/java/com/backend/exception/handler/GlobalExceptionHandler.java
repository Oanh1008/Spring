package com.backend.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Add an exception handler for EntityNotFoundException
    @ExceptionHandler
    public ResponseEntity<JsonErrorResponse> handleException(EntityNotFoundException exc) {
        JsonErrorResponse error = new JsonErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                exc.getMessage(),
                System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }


    // Add another exception handler ... to catch any exception (catch all)
    @ExceptionHandler
    public ResponseEntity<JsonErrorResponse> handleException(Exception exc) {

        JsonErrorResponse error = new JsonErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                exc.getMessage(),
                System.currentTimeMillis());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}