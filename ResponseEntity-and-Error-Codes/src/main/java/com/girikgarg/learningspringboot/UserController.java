package com.girikgarg.learningspringboot;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @GetMapping
    public ResponseEntity<String> getUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("My-Header-1", "SomeValue1");
        headers.add("My-Header-2", "SomeValue2");

        return ResponseEntity.status(HttpStatus.OK).
                headers(headers).body("My return value is okay");
    }

    @GetMapping("/test")
    public ResponseEntity <Void> testUser() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("My-Header-1", "SomeValue1");
        headers.add("My-Header-2", "SomeValue2");
        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers)
                .build();
    }

    @GetMapping("/demo")
    public User demoUser() {
        User user = new User("123", "Girik");
        return user; // spring boot internally wraps the user object into a responseEntity object with status code 200 and returns it
    }

    @GetMapping("/old-get-user")
    public ResponseEntity<Void> getOldUser() {
        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY)
                .header("Location", "/users/demo").build();
    }
}
