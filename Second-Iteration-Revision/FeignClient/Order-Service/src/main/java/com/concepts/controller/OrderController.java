package com.concepts.controller;

import com.concepts.client.ProductClient;
import com.concepts.client.ProductClientWithCustomConfig;
import com.concepts.client.ProductClientWithErrorDecoder;
import com.concepts.client.ProductClientWithRetryer;
import com.concepts.client.ProductClientWithFullControlRetryer;
import com.concepts.client.ProductClientWithTimeout;
import com.concepts.exception.MyCustomBadRequestException;
import com.concepts.exception.MyCustomServerException;
import com.concepts.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    ProductClient productClient;  // Inject the Feign client

    @Autowired
    ProductClientWithCustomConfig productClientWithCustomConfig;  // Inject Feign client with custom config

    @Autowired
    ProductClientWithErrorDecoder productClientWithErrorDecoder;  // Inject Feign client with custom error decoder

    @Autowired
    ProductClientWithRetryer productClientWithRetryer;  // Inject Feign client with custom retryer (UseCase-1)

    @Autowired
    ProductClientWithFullControlRetryer productClientWithFullControlRetryer;  // Inject Feign client with full control retryer (UseCase-2)

    @Autowired
    ProductClientWithTimeout productClientWithTimeout;  // Inject Feign client to test timeout

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrder(@PathVariable String id) {
        
        System.out.println("Order request received for id: " + id);
        
        // Call Product Service using Feign Client
        // No manual HTTP code needed - just call the method!
        String responseFromProductAPI = productClient.getProductById(id);
        
        System.out.println("Response from Product api call is: " + responseFromProductAPI);
        
        return ResponseEntity.ok("order call successful");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateOrderProduct(@PathVariable String id, @RequestBody Product product) {
        
        System.out.println("=== Order Service - Update Product Request ===");
        System.out.println("Product to update: " + product);
        
        // Call Product Service using Feign Client with Custom Encoder/Decoder
        Product updatedProduct = productClientWithCustomConfig.updateProduct(
                id, 
                true,  // sendMail
                "UNIQUE-123-ORDER-SERVICE",  // X-ConceptCoding-ID header
                product
        );
        
        System.out.println("Updated product received from Product Service: " + updatedProduct);
        System.out.println("==============================================");
        
        return ResponseEntity.ok("Product updated successfully via Feign with custom encoder/decoder");
    }

    // Test Custom Error Decoder - 4xx error
    @GetMapping("/test-error/notfound/{id}")
    public ResponseEntity<String> testNotFoundError(@PathVariable String id) {
        System.out.println("=== Testing Custom Error Decoder - 404 ===");
        
        try {
            Product product = productClientWithErrorDecoder.updateProductNotFound(id);
            return ResponseEntity.ok("Unexpected success: " + product);
        } catch (MyCustomBadRequestException e) {
            System.out.println("✓ MyCustomBadRequestException caught: " + e.getMessage());
            return ResponseEntity.status(400).body("Custom 4xx Error Handler worked! Message: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Unexpected exception: " + e.getClass().getName() + " - " + e.getMessage());
            return ResponseEntity.status(500).body("Unexpected error: " + e.getMessage());
        }
    }

    // Test Custom Error Decoder - 5xx error
    @GetMapping("/test-error/server/{id}")
    public ResponseEntity<String> testServerError(@PathVariable String id) {
        System.out.println("=== Testing Custom Error Decoder - 500 ===");
        
        try {
            Product product = productClientWithErrorDecoder.updateProductServerError(id);
            return ResponseEntity.ok("Unexpected success: " + product);
        } catch (MyCustomServerException e) {
            System.out.println("✓ MyCustomServerException caught: " + e.getMessage());
            return ResponseEntity.status(503).body("Custom 5xx Error Handler worked! Message: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Unexpected exception: " + e.getClass().getName() + " - " + e.getMessage());
            return ResponseEntity.status(500).body("Unexpected error: " + e.getMessage());
        }
    }

    // Test Custom Retryer - UseCase-1 (extends Default)
    @GetMapping("/test-retry/usecase1/{id}")
    public ResponseEntity<String> testRetryUseCase1(@PathVariable String id) {
        System.out.println("=== Testing Custom Retryer - UseCase-1 (extends Default) ===");
        
        try {
            long startTime = System.currentTimeMillis();
            String response = productClientWithRetryer.getUnreliableProduct(id);
            long endTime = System.currentTimeMillis();
            
            System.out.println("✓ Success! Response: " + response);
            System.out.println("Total time taken: " + (endTime - startTime) + "ms");
            System.out.println("=====================================================");
            
            return ResponseEntity.ok("UseCase-1 Retry worked! Response: " + response);
        } catch (Exception e) {
            System.out.println("✗ All retries failed: " + e.getMessage());
            return ResponseEntity.status(503).body("All retries exhausted: " + e.getMessage());
        }
    }

    // Test Custom Retryer - UseCase-2 (full control)
    @GetMapping("/test-retry/usecase2/{id}")
    public ResponseEntity<String> testRetryUseCase2(@PathVariable String id) {
        System.out.println("=== Testing Custom Retryer - UseCase-2 (full control) ===");
        
        try {
            long startTime = System.currentTimeMillis();
            String response = productClientWithFullControlRetryer.getUnreliableProduct(id);
            long endTime = System.currentTimeMillis();
            
            System.out.println("✓ Success! Response: " + response);
            System.out.println("Total time taken: " + (endTime - startTime) + "ms");
            System.out.println("======================================================");
            
            return ResponseEntity.ok("UseCase-2 Retry worked! Response: " + response);
        } catch (Exception e) {
            System.out.println("✗ All retries failed: " + e.getMessage());
            return ResponseEntity.status(503).body("All retries exhausted: " + e.getMessage());
        }
    }

    // Test Timeout Configuration
    @GetMapping("/test-timeout/{id}")
    public ResponseEntity<String> testTimeout(@PathVariable String id) {
        System.out.println("=== Testing Feign Client Timeout Configuration ===");
        System.out.println("Configured readTimeout: 2000ms (2 seconds)");
        System.out.println("Product Service will take: 6000ms (6 seconds)");
        System.out.println("Expected: Timeout exception after ~2 seconds");
        
        try {
            long startTime = System.currentTimeMillis();
            String response = productClientWithTimeout.getSlowProduct(id);
            long endTime = System.currentTimeMillis();
            
            System.out.println("✗ Unexpected success! Response: " + response);
            System.out.println("Time taken: " + (endTime - startTime) + "ms");
            
            return ResponseEntity.ok("Unexpected success: " + response);
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();
            long timeTaken = endTime - System.currentTimeMillis();
            System.out.println("✓ Timeout occurred as expected!");
            System.out.println("Exception type: " + e.getClass().getSimpleName());
            System.out.println("Exception message: " + e.getMessage());
            System.out.println("==================================================");
            
            return ResponseEntity.status(408)
                    .body("Request Timeout! Service took longer than configured readTimeout (2 seconds). Exception: " + e.getClass().getSimpleName());
        }
    }
}

