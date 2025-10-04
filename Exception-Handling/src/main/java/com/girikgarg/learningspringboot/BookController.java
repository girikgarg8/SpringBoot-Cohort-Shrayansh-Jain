package com.girikgarg.learningspringboot;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/api")
public class BookController {
    @GetMapping("/books")
    public String getBooks() {
        throw new CustomException(HttpStatus.BAD_REQUEST, "Invalid request for books");
    }

    @GetMapping("/books/test")
    public String getBooksTest() {
        throw new IllegalArgumentException("Invalid request body");
    }

//    @ExceptionHandler(CustomException.class)
//    public ResponseEntity<String> handleCustomException(CustomException e) {
//        return new ResponseEntity<String>(e.getMessage(), e.getStatus());
//    }

//    @ExceptionHandler({IllegalArgumentException.class, CustomException.class})
//    public ResponseEntity<String> handleException(Exception e) {
//        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//    }

//    @ExceptionHandler({IllegalArgumentException.class, CustomException.class})
//    public void handleException(HttpServletResponse response, Exception ex) throws IOException {
//        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
//    }

//    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "This is an exception from response status")
//    @ExceptionHandler
//    public ResponseEntity<String> handleException(CustomException e) {
//        return new ResponseEntity<>("This is an exception from handler", HttpStatus.FORBIDDEN);
//    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "This is an exception from response status")
    @ExceptionHandler
    public void handleException(HttpServletResponse response, CustomException e) throws IOException {
        response.sendError(HttpStatus.FORBIDDEN.value(), "This is an exception from response status");
    }
}
