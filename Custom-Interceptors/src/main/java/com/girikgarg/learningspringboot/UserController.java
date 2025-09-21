package com.girikgarg.learningspringboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/users")
    @MyCustomAnnotation(name="user")
    public String getUsers() {
        System.out.println("Getting user details");
        return "Hello World";
    }
}
