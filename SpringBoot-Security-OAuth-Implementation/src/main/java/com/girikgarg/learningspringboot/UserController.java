package com.girikgarg.learningspringboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @GetMapping("/")
    public String defaultHomePageMethod() {
        return "Hello, you are logged in";
    }

    @GetMapping("/users")
    public String getUserDetails() {
        return "fetched the user details successfully";
    }

}
