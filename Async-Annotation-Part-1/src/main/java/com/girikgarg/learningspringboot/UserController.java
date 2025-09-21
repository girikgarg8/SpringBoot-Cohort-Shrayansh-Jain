package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private AsyncLogger asyncLogger;

    @GetMapping("/users")
    public String getUsers() {
        System.out.println("User controller running on Thread " + Thread.currentThread().getName());
        asyncLogger.log();
        return "Hello";
    }
}
