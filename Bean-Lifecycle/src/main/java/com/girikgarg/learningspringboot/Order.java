package com.girikgarg.learningspringboot;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class Order {
    public Order() {
        System.out.println("Lazy: initializing order");
    }

    @PostConstruct
    public void init() {
        System.out.println("PostConstruct: initializing order");
    }
}
