package com.concepts.client;

import com.concepts.config.ProductClientConfig;
import com.concepts.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

// Feign Client with custom ErrorDecoder configuration
@FeignClient(name = "product-service-error", 
             url = "${feign.client.product-service.url}",
             configuration = ProductClientConfig.class)
public interface ProductClientWithErrorDecoder {

    // Endpoint that will trigger 404 error
    @PutMapping(value = "/products/update/error/notfound/{id}")
    Product updateProductNotFound(@PathVariable("id") String id);

    // Endpoint that will trigger 500 error
    @PutMapping(value = "/products/update/error/server/{id}")
    Product updateProductServerError(@PathVariable("id") String id);
}

