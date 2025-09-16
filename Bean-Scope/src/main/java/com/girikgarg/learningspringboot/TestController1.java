package com.girikgarg.learningspringboot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/v1")
public class TestController1 {
    @Autowired
    private User user;

    @GetMapping("/users")
    public ResponseEntity<String> getUsers() {
        System.out.println("Test1 hashcode is " + hashCode() + " and User hashcode is " + user.hashCode());
        return ResponseEntity.ok("Hello World");
    }

    @PostConstruct
    public void init() {
        System.out.println("Test1 construction completed");
    }
}
