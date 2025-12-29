package com.girikgarg.learningspringboot;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    
    @GetMapping("/")
    public String home(Authentication auth) {
        return "Welcome " + auth.getName() + "! You're authenticated via HTTP Basic.";
    }
    
    @GetMapping("/user-info")
    public Map<String, Object> getUserInfo(Authentication auth) {
        Map<String, Object> info = new HashMap<>();
        info.put("username", auth.getName());
        info.put("authorities", auth.getAuthorities());
        return info;
    }
}
