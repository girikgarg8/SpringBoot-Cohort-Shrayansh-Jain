package com.concepts.exception;

public class MyCustomBadRequestException extends RuntimeException {
    public MyCustomBadRequestException(String message) {
        super(message);
    }
}

