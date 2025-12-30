package com.girikgarg.orderservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service")
public interface ProductClient {
    
    @GetMapping(value = "/products/{id}")
    String getProductById(@PathVariable("id") String id);
    
    @GetMapping(value = "/products")
    String getAllProducts();
}

