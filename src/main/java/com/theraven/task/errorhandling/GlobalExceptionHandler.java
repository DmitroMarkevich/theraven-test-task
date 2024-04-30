package com.theraven.task.errorhandling;

import com.theraven.task.errorhandling.exception.CustomerExistsException;
import com.theraven.task.errorhandling.exception.CustomerNotFoundException;
import com.theraven.task.errorhandling.exception.CustomerValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for handling specific exceptions
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    private ResponseEntity<Object> handleException(IllegalArgumentException exception) {
        return ErrorUtils.buildExceptionBody(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CustomerValidationException.class)
    private ResponseEntity<Object> handleException(CustomerValidationException exception) {
        return ErrorUtils.buildExceptionBody(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomerExistsException.class)
    private ResponseEntity<Object> handleException(CustomerExistsException exception) {
        return ErrorUtils.buildExceptionBody(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    private ResponseEntity<Object> handleException(CustomerNotFoundException exception) {
        return ErrorUtils.buildExceptionBody(exception, HttpStatus.NOT_FOUND);
    }
}
