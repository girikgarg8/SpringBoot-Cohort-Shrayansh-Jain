package com.girikgarg.learningspringboot;

import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SameClassAsyncTest {
    @GetMapping("/orders")
    public String getOrders() {
        System.out.println("get orders running on thread: " + Thread.currentThread().getName());
        log();
        return "Hello World";
    }

    @Async
    public void log() {
        System.out.println("Logger running on thread: " + Thread.currentThread().getName());
    }
}
