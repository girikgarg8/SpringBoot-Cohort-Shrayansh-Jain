package com.girikgarg.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DemoController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot Actuator Demo!";
    }

    @GetMapping("/simulate-delay")
    public String simulateDelay() throws InterruptedException {
        Thread.sleep(2000); // Simulate slow endpoint
        return "Delayed response (useful for testing metrics)";
    }

    @GetMapping("/simulate-error")
    public String simulateError() {
        throw new RuntimeException("Simulated error for testing");
    }
}

