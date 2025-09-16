package com.girikgarg.learningspringboot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Scope("session")
@RequestMapping("/api/v5")
public class TestController5 {
    @Autowired
    private Student student;

    @GetMapping("/users")
    public String getUsers() {
        System.out.println("TestController5 bean hashcode" + this.hashCode());
        return "Hello world";
    }
}
