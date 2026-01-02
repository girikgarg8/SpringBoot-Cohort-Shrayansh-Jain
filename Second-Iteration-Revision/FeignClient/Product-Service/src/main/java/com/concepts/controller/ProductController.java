package com.concepts.controller;

import com.concepts.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/products")
public class ProductController {

    // Use AtomicInteger for thread-safe counter
    private final AtomicInteger callCount = new AtomicInteger(0);

    @GetMapping("/{id}")
    public String getProduct(@PathVariable String id) {
        System.out.println("Product request received for id: " + id);
        return "Product fetched with id: " + id;
    }

    // Slow endpoint - simulates timeout scenarios
    @GetMapping("/slow/{id}")
    public ResponseEntity<String> getSlowProduct(@PathVariable String id) {
        System.out.println("=== Slow endpoint called for id: " + id + " ===");
        System.out.println("⏳ Simulating slow processing (6 seconds)...");
        
        try {
            Thread.sleep(6000);  // Sleep for 6 seconds (more than readTimeout of 5000ms)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread interrupted: " + e.getMessage());
        }
        
        System.out.println("✓ Slow endpoint completed");
        return ResponseEntity.ok("Product fetched with id: " + id + " (after 6 seconds)");
    }

    // Unreliable endpoint - fails first 3 times, succeeds on 4th attempt
    @GetMapping("/unreliable/{id}")
    public ResponseEntity<String> getUnreliableProduct(@PathVariable String id) {
        int currentCount = callCount.incrementAndGet();
        System.out.println("=== Unreliable endpoint called (attempt #" + currentCount + ") ===");
        
        if (currentCount < 4) {
            System.out.println("✗ Failing with 503 Service Unavailable (attempt " + currentCount + "/3)");
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("Service temporarily unavailable");
        } else {
            System.out.println("✓ Success! Returning product (attempt " + currentCount + ")");
            callCount.set(0);  // Reset for next test
            return ResponseEntity.ok("Product fetched with id: " + id + " (succeeded after retry)");
        }
    }

    @PutMapping("/update/{id}")
    public Product updateProduct(
            @PathVariable String id,
            @RequestParam("sendMail") boolean sendMail,
            @RequestHeader("X-ConceptCoding-ID") String uniqueID,
            @RequestBody Product updatedProductDetails) {
        
        System.out.println("=== Product Update Request Received ===");
        System.out.println("Path Variable - id: " + id);
        System.out.println("Request Param - sendMail: " + sendMail);
        System.out.println("Request Header - X-ConceptCoding-ID: " + uniqueID);
        System.out.println("Request Body - Product: " + updatedProductDetails);
        System.out.println("========================================");
        
        // Simulate product update
        updatedProductDetails.setId(id);
        return updatedProductDetails;
    }

    // Simulate 404 error
    @PutMapping("/update/error/notfound/{id}")
    public ResponseEntity<String> updateProductNotFound(@PathVariable String id) {
        System.out.println("Simulating 404 error for product id: " + id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
    }

    // Simulate 500 error
    @PutMapping("/update/error/server/{id}")
    public ResponseEntity<String> updateProductServerError(@PathVariable String id) {
        System.out.println("Simulating 500 error for product id: " + id);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
    }
}

