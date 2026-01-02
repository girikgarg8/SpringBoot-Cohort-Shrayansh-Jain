package com.concepts.exception;

public class MyCustomServerException extends RuntimeException {
    public MyCustomServerException(String message) {
        super(message);
    }
}

