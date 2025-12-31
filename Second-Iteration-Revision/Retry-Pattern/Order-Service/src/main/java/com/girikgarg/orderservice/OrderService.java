package com.girikgarg.orderservice;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class OrderService {

    @Autowired
    ProductClient productClient;

    @Autowired
    io.github.resilience4j.retry.Retry customRetry;

    /**
     * FIXED INTERVAL RETRY
     * Retries with a fixed 2-second delay between attempts
     * Max 3 attempts
     */
    @Retry(name = "productService", fallbackMethod = "productFallback")
    public String invokeProductAPIFixedInterval(String id) {
        System.out.println("calling product service at " + LocalTime.now());
        String response = productClient.getProductById(id);
        return response;
    }

    /**
     * EXPONENTIAL BACKOFF RETRY
     * Wait time increases exponentially: 1s, 2s, 4s, 8s...
     * Max 4 attempts
     */
    @Retry(name = "productServiceExponential", fallbackMethod = "productFallback")
    public String invokeProductAPIExponential(String id) {
        System.out.println("calling product service at " + LocalTime.now());
        String response = productClient.getProductById(id);
        return response;
    }

    /**
     * EXPONENTIAL BACKOFF WITH JITTER
     * Same as exponential but with randomization to prevent thundering herd
     * Max 4 attempts
     */
    @Retry(name = "productServiceJitter", fallbackMethod = "productFallback")
    public String invokeProductAPIJitter(String id) {
        System.out.println("calling product service at " + LocalTime.now());
        String response = productClient.getProductById(id);
        return response;
    }

    /**
     * CUSTOM RETRY
     * Uses custom Retry bean defined in Config.java
     * Need to manually wrap the Retry object
     * @Retry annotation works only for those Retry types for which we can provide config in application.properties
     */
    public String invokeProductAPICustom(String id) {
        return customRetry.executeSupplier(() -> {
            System.out.println("calling product service at " + LocalTime.now());
            return productClient.getProductById(id);
        });
    }

    /**
     * Fallback method invoked when all retries fail
     */
    public String productFallback(String id, Throwable t) {
        System.out.println("All retries failed. This is fallback");
        return "Product Service is busy";
    }
}
