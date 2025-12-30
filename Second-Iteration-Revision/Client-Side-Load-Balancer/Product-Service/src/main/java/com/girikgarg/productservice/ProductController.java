package com.girikgarg.productservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Value("${server.port}")
    private String port;
    
    @GetMapping
    public String getAllProducts() {
        return "All products from Product Service on PORT: " + port;
    }
    
    @GetMapping("/{id}")
    public String getProductById(@PathVariable String id) {
        return "Product with ID: " + id + " from Product Service on PORT: " + port;
    }
    
    @GetMapping("/health")
    public String health() {
        return "Product Service is running on PORT: " + port;
    }
}

