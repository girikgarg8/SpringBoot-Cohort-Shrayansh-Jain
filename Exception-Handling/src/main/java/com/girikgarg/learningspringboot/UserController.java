package com.girikgarg.learningspringboot;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class UserController {
    @GetMapping(path = "/get-user")
    public String getUser() {
        throw new NullPointerException("Throwing null pointer exception for testing");
    }

    @GetMapping(path = "/test-user")
    public ResponseEntity<?> testUser() {

        try {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid request sent");
        }
        catch (CustomException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
        catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
