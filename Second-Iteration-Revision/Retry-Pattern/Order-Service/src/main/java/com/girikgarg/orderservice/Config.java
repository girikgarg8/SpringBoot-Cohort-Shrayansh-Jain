package com.girikgarg.orderservice;

import io.github.resilience4j.core.IntervalFunction;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    /**
     * Custom Retry with IntervalFunction
     * 
     * IntervalFunction defines the wait time between retries.
     * Here we use a Fibonacci-like sequence: 2s, 2s, 4s, 6s...
     */
    @Bean
    public Retry customRetry() {
        // IntervalFunction defines the wait time between retries
        // attempt -> milliseconds (as long as you want)
        IntervalFunction fibonacciInterval = attempt -> {
            return 2000L; // Return 2000ms = 2 seconds for all retries
        };

        // Create RetryConfig with all settings
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(4) // Maximum number of retry attempts
                .intervalFunction(fibonacciInterval) // Custom interval function
                .retryExceptions(Exception.class) // Retry on all exceptions
                .build();

        // Create and return Retry object from RetryConfig
        // We can give any name to our Retry
        return Retry.of("customRetry", config);
    }
}

