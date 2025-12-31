package com.concepts.controller;

import com.concepts.config.OrderProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Order Controller demonstrating @RefreshScope functionality
 * 
 * The counter demonstrates that the bean is recreated when properties are refreshed
 * but the counter value persists because it's stored in the controller instance
 */
@RestController
@RequestMapping("/orders")
public class OrderController {
    
    // Counter to track number of times endpoint is called
    private int counter = 0;
    
    // Autowire the OrderProperties which has @RefreshScope
    @Autowired
    OrderProperties orderProperties;
    
    /**
     * GET /orders/fetch
     * 
     * Returns the message from config server along with the counter value
     * Counter increments on each call to demonstrate bean state
     */
    @GetMapping("/fetch")
    public String getOrders() {
        counter++;
        return "fetched orders and message: " + orderProperties.getMessage() + 
               " and counter value is: " + counter;
    }
}


