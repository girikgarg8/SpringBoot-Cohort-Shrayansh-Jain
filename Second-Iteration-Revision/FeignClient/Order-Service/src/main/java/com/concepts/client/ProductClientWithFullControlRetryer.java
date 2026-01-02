package com.concepts.client;

import com.concepts.config.ProductClientConfigWithFullControlRetryer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Feign Client with full control Retryer (UseCase-2 - implements Retryer)
@FeignClient(name = "product-service-retry-full", 
             url = "${feign.client.product-service.url}",
             configuration = ProductClientConfigWithFullControlRetryer.class)
public interface ProductClientWithFullControlRetryer {

    @GetMapping("/products/unreliable/{id}")
    String getUnreliableProduct(@PathVariable("id") String id);
}

