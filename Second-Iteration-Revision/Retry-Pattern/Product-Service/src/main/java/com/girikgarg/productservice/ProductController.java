package com.girikgarg.productservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    @GetMapping
    public String getAllProducts() {
        return "All products from Product Service";
    }
    
    @GetMapping("/{id}")
    public String getProductById(@PathVariable String id) {
        return "Product with ID: " + id + " from Product Service";
    }
    
    @GetMapping("/health")
    public String health() {
        return "Product Service is running on port 8082";
    }
}

