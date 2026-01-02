package com.concepts.controller;

import com.concepts.model.ProductEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    // GET endpoint
    @GetMapping("/{id}")
    public ResponseEntity<String> getProduct(@PathVariable String id, 
                                             @RequestHeader HttpHeaders headers) {
        System.out.println("Product request received for id: " + id);
        
        // Log all headers received
        System.out.println("=== Headers Received in Product Service ===");
        headers.forEach((key, value) -> {
            System.out.println(key + " = " + value);
        });
        
        // Check for custom header
        if (headers.containsKey("x-custom-header")) {
            System.out.println("✓ Custom header found: x-custom-header = " + headers.getFirst("x-custom-header"));
        } else {
            System.out.println("✗ Custom header NOT found");
        }
        System.out.println("==========================================");
        
        // Simulate product not found for ID 999 or higher
        if ("999".equals(id) || Integer.parseInt(id) >= 999) {
            System.out.println("Product not found with id: " + id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product not found with id: " + id);
        }
        
        System.out.println("Product fetched with id: " + id);
        return ResponseEntity.ok("Product fetched with id: " + id);
    }

    // POST endpoint - Create new product
    @PostMapping("/create")
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity productEntity) {
        System.out.println("Product created: " + productEntity);
        // In real scenario, save to database and return with ID
        return ResponseEntity.status(HttpStatus.CREATED).body(productEntity);
    }

    // DELETE endpoint - Delete product by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String id) {
        System.out.println("Product deleted with id: " + id);
        // In real scenario, delete from database
        return ResponseEntity.noContent().build();
    }
}

