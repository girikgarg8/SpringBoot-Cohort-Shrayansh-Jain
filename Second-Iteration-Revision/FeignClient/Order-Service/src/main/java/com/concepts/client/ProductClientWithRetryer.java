package com.concepts.client;

import com.concepts.config.ProductClientConfigWithRetryer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Feign Client with custom Retryer (UseCase-1 - extends Default)
@FeignClient(name = "product-service-retry", 
             url = "${feign.client.product-service.url}",
             configuration = ProductClientConfigWithRetryer.class)
public interface ProductClientWithRetryer {

    @GetMapping("/products/unreliable/{id}")
    String getUnreliableProduct(@PathVariable("id") String id);
}

