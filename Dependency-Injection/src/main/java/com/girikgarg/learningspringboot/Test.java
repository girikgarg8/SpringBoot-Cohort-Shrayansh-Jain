package com.girikgarg.learningspringboot;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Test {
    private Order order;
    private User user;

    public Test(Order order) {
        this.order = order;
    }

    @Autowired
    public Test(User user) {
        this.user = user;
    }

    @PostConstruct
    public void init() {
        System.out.println("Order is null in test " + (order==null) ) ;
    }


}
