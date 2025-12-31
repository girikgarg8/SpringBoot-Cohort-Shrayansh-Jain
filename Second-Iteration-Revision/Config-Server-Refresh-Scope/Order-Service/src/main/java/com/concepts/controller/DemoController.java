package com.concepts.controller;

import com.concepts.config.OrderProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Demo Controller with @RefreshScope to demonstrate BEAN RECREATION and STATE LOSS
 * 
 * KEY BEHAVIOR:
 * - When POST /actuator/refresh is called, this ENTIRE BEAN is DESTROYED and RECREATED
 * - All instance variables (like counter) are RESET to their initial values
 * - This is because @RefreshScope wraps the bean in a proxy that handles refresh events
 * 
 * CONTRAST WITH OrderController:
 * - OrderController does NOT have @RefreshScope
 * - OrderController's counter persists across refreshes
 * - DemoController's counter RESETS on every refresh
 * 
 * USE CASES:
 * - Use @RefreshScope on controllers ONLY if you want to refresh @Value properties
 * - Generally, DON'T use @RefreshScope on controllers to avoid state loss
 * - Better pattern: Use @RefreshScope on @ConfigurationProperties beans (like OrderProperties)
 *   and autowire them in controllers
 */
@RestController
@RefreshScope  // ← This causes the bean to be destroyed and recreated on refresh!
@RequestMapping("/demo")
public class DemoController {
    
    /**
     * Counter to track API calls
     * 
     * IMPORTANT: This counter will RESET to 0 every time /actuator/refresh is called
     * because the entire bean is destroyed and recreated!
     */
    private int counter = 0;
    
    /**
     * Autowired OrderProperties (which also has @RefreshScope)
     * 
     * After refresh:
     * - This will point to the NEW OrderProperties bean instance
     * - The message value will be updated from Config Server
     */
    @Autowired
    OrderProperties orderProperties;
    
    /**
     * GET /demo/test
     * 
     * Demonstrates:
     * 1. Counter increments on each call
     * 2. Counter RESETS to 1 after /actuator/refresh (bean recreation)
     * 3. Message updates from Config Server after refresh
     * 
     * @return Response with message and counter
     */
    @GetMapping("/test")
    public String test() {
        counter++;
        return "Demo endpoint - message: " + orderProperties.getMessage() + 
               " | counter (WILL RESET on refresh): " + counter;
    }
    
    /**
     * GET /demo/info
     * 
     * Provides information about this bean's lifecycle
     * 
     * @return Bean lifecycle information
     */
    @GetMapping("/info")
    public String info() {
        return "This controller has @RefreshScope. " +
               "Current counter: " + counter + 
               ". This counter will RESET to 0 when /actuator/refresh is called " +
               "because the bean is destroyed and recreated!";
    }
}

