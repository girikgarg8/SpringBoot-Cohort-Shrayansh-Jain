package com.girikgarg.learningspringboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    
    @GetMapping("/")
    public String defaultHomePage() {
        return "Hello, you are logged in";
    }
    
    @GetMapping("/users")
    public String getUserDetails() {
        return "Fetched user details successfully";
    }
}
