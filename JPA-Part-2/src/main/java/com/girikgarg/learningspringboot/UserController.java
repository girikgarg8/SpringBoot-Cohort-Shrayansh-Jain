package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class UserController {
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping(path="/test-jpa")
    public List<UserDetails> getUser() {
        UserDetails userDetails = new UserDetails("xyz", "xyz@abc.com");
        userDetailsService.saveUser2(userDetails);
        return userDetailsService.getAllUsers();
    }
}
