package com.concepts.client;

import com.concepts.config.ProductClientConfig;
import com.concepts.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

// All custom configuration defined in ProductClientConfig is applicable for this ProductClient only
// We can have many ClientConfig like SalesClientConfig, InventoryClientConfig etc.
// Each configuration can have its own customization - Not impacting each other
@FeignClient(name = "product-service-custom", 
             url = "${feign.client.product-service.url}",
             configuration = ProductClientConfig.class)
public interface ProductClientWithCustomConfig {

    @PutMapping(value = "/products/update/{id}", consumes = "application/json")
    Product updateProduct(
            @PathVariable("id") String id,
            @RequestParam("sendMail") boolean sendMail,
            @RequestHeader("X-ConceptCoding-ID") String uniqueID,
            @RequestBody Product updatedProductDetails
    );
}

