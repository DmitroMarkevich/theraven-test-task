package com.theraven.task.errorhandling.exception;

public class CustomerExistsException extends RuntimeException {

    public CustomerExistsException(String message) {
        super(message);
    }
}