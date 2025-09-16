package com.girikgarg.learningspringboot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/v3")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TestController3 {
    @Autowired
    private User user;

    @Autowired
    private Student student;

    @GetMapping("/users")
    public ResponseEntity<String> getUsers() {
        System.out.println("Test3 hashcode is " + hashCode() + "User hashcode is " + user.hashCode() + "and student hashCode is " + student.hashCode());
        return ResponseEntity.ok("Hello World");
    }

    @PostConstruct
    public void init() {
        System.out.println("Test3 construction completed");
    }
}
