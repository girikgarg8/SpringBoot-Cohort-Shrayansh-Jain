package com.girikgarg.learningspringboot;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class User {
    @Autowired
    private Order order;

    @PostConstruct
    public void init() {
        System.out.println("PostConstruct: initializing user");
    }

    private String id;

    public User(String id) {
        this.id = id;
    }

    public User() {
        this.id = "456"; // for testing
        System.out.println("User initializing");
    }

    public String getId() {
        return id;
    }

    @PreDestroy
    public void destroy() {
        System.out.println("PreDestroy: destroying user");
    }
}
