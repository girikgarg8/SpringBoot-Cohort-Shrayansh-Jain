package com.girikgarg.learningspringboot;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class Student {
    @PostConstruct
    public void init() {
        System.out.println("Student construction completed");
    }

}
