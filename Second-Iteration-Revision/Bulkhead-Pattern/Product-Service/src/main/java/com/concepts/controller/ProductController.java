package com.concepts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/{id}")
    public String getProductById(@PathVariable String id) throws InterruptedException {
        // Simulate some processing delay
        Thread.sleep(2000); // 2 seconds delay
        System.out.println("Product API called for ID: " + id);
        return "Product details for ID: " + id;
    }
}


