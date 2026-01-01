package com.concepts.controller;

import com.concepts.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    // ==================== RESILIENCE4J RATE LIMITER ENDPOINT ====================
    @GetMapping("/resilience4j/{id}")
    public void callProductAPI(@PathVariable String id) {
        orderService.invokeProductAPI(id);
    }

    // ==================== CUSTOM RATE LIMITER ENDPOINT ====================
    @GetMapping("/custom/products")
    public ResponseEntity<String> getProducts() {
        try {
            String result = orderService.getProducts();
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(429).body(e.getMessage());
        }
    }
}

