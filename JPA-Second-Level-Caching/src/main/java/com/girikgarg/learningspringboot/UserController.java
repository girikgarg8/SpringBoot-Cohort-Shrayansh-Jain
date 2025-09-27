package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class UserController {
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping(path="/test-jpa")
    public Optional<UserDetails> getUser() {
        UserDetails userDetails = new UserDetails("xyz", "xyz@abc.com");
        userDetailsService.saveUser(userDetails);
        return userDetailsService.findById(1L);
    }

    @GetMapping(path="/read-jpa")
    public Optional <UserDetails> readUser() {
        return userDetailsService.findById(1L);
    }


    @GetMapping(path = "/test-persistence-context")
    public UserDetails createUser2() {
        return userDetailsService.testUser(new UserDetails("ABC", "xyz@abc.com"));
    }

    @GetMapping(path="/create-jpa")
    public void createUser() {
        userDetailsService.saveUser(new UserDetails("xyz", "xyz@abc.com"));
    }

    @GetMapping(path="/edit-jpa")
    public UserDetails editReadOnly() {
        return userDetailsService.editEntity(1L);
    }
}
