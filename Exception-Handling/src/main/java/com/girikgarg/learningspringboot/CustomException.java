package com.girikgarg.learningspringboot;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ResponseStatus(HttpStatus.BAD_REQUEST)
//@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Somthing wrong due to which custom exception")
public class CustomException extends RuntimeException {
    private HttpStatus status;

    public CustomException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
