package com.girikgarg.orderservice;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.event.CircuitBreakerOnStateTransitionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class CircuitBreakerConfig {

    @Autowired
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @PostConstruct
    public void registerEventListener() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("productService");
        
        // Log all state transitions
        circuitBreaker.getEventPublisher()
            .onStateTransition(event -> {
                System.out.println("\n========================================");
                System.out.println("🔄 CIRCUIT BREAKER STATE TRANSITION");
                System.out.println("========================================");
                System.out.println("From State: " + event.getStateTransition().getFromState());
                System.out.println("To State: " + event.getStateTransition().getToState());
                System.out.println("Time: " + event.getCreationTime());
                System.out.println("========================================\n");
            });
        
        // Log when circuit breaker opens
        circuitBreaker.getEventPublisher()
            .onError(event -> {
                System.out.println("❌ Circuit Breaker recorded error: " + event.getThrowable().getClass().getSimpleName());
            });
        
        // Log when call is not permitted (circuit is open)
        circuitBreaker.getEventPublisher()
            .onCallNotPermitted(event -> {
                System.out.println("⛔ Call NOT PERMITTED - Circuit is OPEN!");
            });
        
        // Log successful calls
        circuitBreaker.getEventPublisher()
            .onSuccess(event -> {
                System.out.println("✅ Circuit Breaker recorded success - Duration: " + event.getElapsedDuration().toMillis() + "ms");
            });
    }
}

