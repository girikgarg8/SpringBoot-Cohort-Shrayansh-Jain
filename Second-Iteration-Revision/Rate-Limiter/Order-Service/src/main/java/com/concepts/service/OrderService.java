package com.concepts.service;

import com.concepts.client.ProductClient;
import com.concepts.ratelimiter.CustomRateLimiter;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderService {

    @Autowired
    ProductClient productClient;

    // ==================== RESILIENCE4J RATE LIMITER ====================
    // Internally it uses AOP functionality, that's why the
    // method on which this @RateLimiter annotation applies, 
    // need to be public and Spring managed bean.
    
    // Bucket filled with 2 tokens every 10second,
    // If there is no token wait for 1sec before rejecting the request
    @RateLimiter(name = "productRateLimiter", fallbackMethod = "rateLimitedFallback")
    public void invokeProductAPI(String id) {
        String response = productClient.getProductById(id);
        System.out.println("[Resilience4j] Response from Product api call is: " + response);
    }

    // Fallback method, return type and parameter (additionally only Throwable need to
    // add more) should match with the original method, else default fallback method
    // provided by RateLimiter framework will get invoked.
    public void rateLimitedFallback(String id, Throwable t) {
        System.out.println("[Resilience4j] Rate limit exceeded. Try later!");
        // throw exception here and handle it gracefully
    }

    // ==================== CUSTOM RATE LIMITER ====================
    // Custom rate limiting logic using AOP and sliding window algorithm
    // 5 requests allowed in 60 second window
    @CustomRateLimiter(limit = 5, windowInSeconds = 60)
    public String getProducts() {
        // Service call
        String response = productClient.getProductById("custom-rate-limit");
        System.out.println("[Custom] Response: " + response);
        return "List of products";
    }
}

