package com.girikgarg.orderservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @GetMapping
    public String getAllOrders() {
        return "All orders from Order Service";
    }
    
    @GetMapping("/health")
    public String health() {
        return "Order Service is running on port 8081";
    }
    
    /**
     * FIXED INTERVAL RETRY
     * Endpoint: GET /orders/retry-fixed/{id}
     * Retries with fixed 2-second delay
     */
    @GetMapping("/retry-fixed/{id}")
    public String callProductWithFixedRetry(@PathVariable String id) {
        String response = orderService.invokeProductAPIFixedInterval(id);
        return "Order Service called Product Service (Fixed Interval Retry): " + response;
    }
    
    /**
     * EXPONENTIAL BACKOFF RETRY
     * Endpoint: GET /orders/retry-exponential/{id}
     * Retries with exponentially increasing delay: 1s, 2s, 4s, 8s
     */
    @GetMapping("/retry-exponential/{id}")
    public String callProductWithExponentialRetry(@PathVariable String id) {
        String response = orderService.invokeProductAPIExponential(id);
        return "Order Service called Product Service (Exponential Backoff): " + response;
    }
    
    /**
     * EXPONENTIAL BACKOFF WITH JITTER
     * Endpoint: GET /orders/retry-jitter/{id}
     * Retries with exponential delay + randomization
     */
    @GetMapping("/retry-jitter/{id}")
    public String callProductWithJitterRetry(@PathVariable String id) {
        String response = orderService.invokeProductAPIJitter(id);
        return "Order Service called Product Service (Exponential Backoff + Jitter): " + response;
    }
    
    /**
     * CUSTOM RETRY
     * Endpoint: GET /orders/retry-custom/{id}
     * Retries with custom IntervalFunction
     */
    @GetMapping("/retry-custom/{id}")
    public String callProductWithCustomRetry(@PathVariable String id) {
        try {
            String response = orderService.invokeProductAPICustom(id);
            return "Order Service called Product Service (Custom Retry): " + response;
        } catch (Exception e) {
            System.out.println("All retries failed. This is fallback");
            return "Order Service called Product Service (Custom Retry): Product Service is busy";
        }
    }
}
