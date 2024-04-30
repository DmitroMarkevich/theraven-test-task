package com.theraven.task.errorhandling;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.Instant;
import java.util.List;

/**
 * Utility class for handling errors and exceptions
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorUtils {

    /**
     * Builds an exception response body
     *
     * @param exception  The exception to be handled
     * @param httpStatus The HTTP status code to be returned
     * @return A ResponseEntity containing the error response body
     */
    public static ResponseEntity<Object> buildExceptionBody(Exception exception, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(ErrorResponse.builder()
                .statusCode(httpStatus.value())
                .timestamp(Instant.now())
                .message(httpStatus.name())
                .details(List.of(exception.getMessage()))
                .build()
        );
    }

    /**
     * Handles validation errors and retrieves error messages.
     *
     * @param bindingResult The BindingResult containing validation errors.
     * @return A list of error messages.
     */
    public static List<String> handleValidationErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .toList();
    }
}