package com.girikgarg.learningspringboot;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class User {
//    @Autowired
    private Order order;
    private String id;

    @Autowired
    public User(Order order) {
        this.order = order;
        this.id = "456"; // for testing
    }

    public User(String id) {
        this.id = id;
    }

    @PostConstruct
    public void init() {
        System.out.println("PostConstruct: initializing user");
    }


//    public User(String id) {
//        this.id = id;
//    }
//
//    public User() {
//        this.id = "456"; // for testing
//        System.out.println("User initializing");
//    }

//    @Autowired
//    public void setOrder(Order order) {
//        this.order = order;
//    }

    public String getId() {
        return id;
    }

    @PreDestroy
    public void destroy() {
        System.out.println("PreDestroy: destroying user");
    }
}
