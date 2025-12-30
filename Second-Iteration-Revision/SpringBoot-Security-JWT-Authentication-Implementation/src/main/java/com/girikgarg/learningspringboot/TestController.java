package com.girikgarg.learningspringboot;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    
    @GetMapping("/api/hello")
    public String hello() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "Hello, " + auth.getName() + "! JWT authentication successful.";
    }
    
    @GetMapping("/api/user-info")
    public String userInfo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return "Username: " + auth.getName() + ", Authorities: " + auth.getAuthorities();
    }
}

