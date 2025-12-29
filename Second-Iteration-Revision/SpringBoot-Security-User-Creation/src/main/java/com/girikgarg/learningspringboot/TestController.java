package com.girikgarg.learningspringboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {
    
    @GetMapping("/test")
    public String test() {
        return "This is a protected endpoint. You are authenticated!";
    }
    
    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Security!";
    }
}

