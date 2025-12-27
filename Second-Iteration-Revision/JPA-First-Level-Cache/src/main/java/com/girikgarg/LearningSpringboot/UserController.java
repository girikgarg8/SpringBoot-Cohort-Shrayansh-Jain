package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/")
public class UserController {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @GetMapping(path = "/test-jpa")
    public UserDetails getUser() {
        UserDetails userDetails = new UserDetails("xyz", "xyz@conceptandcoding.com");
        userDetailsService.saveUser(userDetails);
        UserDetails output1 = userDetailsService.getUser(1L);
        return output1;
    }
    
    @GetMapping(path = "/read-jpa")
    public UserDetails getUser2() {
        UserDetails output1 = userDetailsService.getUser(1L);
        return output1;
    }
    
    @GetMapping(path = "/demo")
    public UserDetails demoL1Cache() {
        UserDetails userDetails = new UserDetails("xyz", "xyz@conceptandcoding.com");
        UserDetails output = userDetailsService.testSaveUser(userDetails);
        return output;
    }
}

