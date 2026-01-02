package com.concepts.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// name: Logical name of the Feign client
// url: Base URL of the Product Service (from application.properties)
@FeignClient(name = "product-service", url = "${feign.client.product-service.url}")
public interface ProductClient {

    // Just declare what to call - no implementation needed!
    // Feign generates the implementation automatically
    @GetMapping("/products/{id}")
    String getProductById(@PathVariable("id") String id);
}

