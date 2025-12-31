package com.girikgarg.orderservice;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderService {

    @Autowired
    ProductClient productClient;

    @CircuitBreaker(name = "productService", fallbackMethod = "fallback")
    public String invokeProductAPI(String id) {
        return productClient.getProductById(id);
    }

    public String fallback(String id, Throwable ex) {
        System.out.println("===== CIRCUIT BREAKER ACTIVATED =====");
        System.out.println("Requested Product ID: " + id);
        System.out.println("Exception Type: " + ex.getClass().getName());
        System.out.println("Exception Message: " + ex.getMessage());
        System.out.println("Root Cause: " + (ex.getCause() != null ? ex.getCause().getMessage() : "No cause"));
        System.out.println("=====================================");
        return "Product Service is currently unavailable. Please try again later. (Circuit Breaker Fallback)";
    }
}

