package com.girikgarg.learningspringboot;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class User {

    @PostConstruct
    public void init() {
        System.out.println("User construction completed");
    }
}
