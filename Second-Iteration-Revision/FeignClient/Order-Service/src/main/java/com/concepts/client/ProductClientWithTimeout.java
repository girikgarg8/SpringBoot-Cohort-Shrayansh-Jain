package com.concepts.client;

import com.concepts.config.ProductClientConfigWithTimeout;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Feign Client to test timeout configuration
// Timeout is configured programmatically in ProductClientConfigWithTimeout
// connectTimeout=1000ms, readTimeout=2000ms
@FeignClient(name = "product-service-timeout", 
             url = "${feign.client.product-service.url}",
             configuration = ProductClientConfigWithTimeout.class)
public interface ProductClientWithTimeout {

    // This will timeout because Product Service takes 6 seconds
    // but our readTimeout is configured to 2000ms (2 seconds)
    @GetMapping("/products/slow/{id}")
    String getSlowProduct(@PathVariable("id") String id);
}


