package com.concepts.controller;

import com.concepts.exception.InternalError;
import com.concepts.exception.MyCustomException;
import com.concepts.exception.ServerException;
import com.concepts.model.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    RestClient restClient;

    // ==================== GET DEMO ====================
    @GetMapping("/{id}")
    public ResponseEntity<String> getOrder(@PathVariable String id) {
        
        // Same template, we are going to use for all
        // different operation like post, put, delete etc.
        // No different methods for different operations like RestTemplate
        String response = restClient
                .get()
                .uri( "http://localhost:8082/products/" + id)
                .retrieve()
                .body(String.class);
        
        System.out.println("Response from Product API called from order service: " + response);
        
        return ResponseEntity.ok("order call successful");
    }

    // ==================== POST DEMO ====================
    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody ProductEntity productEntity) {
        
        ResponseEntity<ProductEntity> response = restClient
                .post()
                .uri("http://localhost:8082/products/create")
                .accept(MediaType.APPLICATION_JSON)
                .header("Content-Type", "application/json")
                .body(new ProductEntity("Ice-cream", 29.99)) // some new object which need to be created
                .retrieve()
                .toEntity(ProductEntity.class);
        
        ProductEntity responseBody = response.getBody();
        System.out.println("Response from Product API: " + responseBody);
        
        return ResponseEntity.ok("order created successfully");
    }

    // ==================== DELETE DEMO ====================
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable String id) {
        
        ResponseEntity<Void> response = restClient
                .delete()
                .uri("http://localhost:8082/products/" + id)
                .retrieve()
                .toBodilessEntity();
        
        HttpStatusCode deletionStatus = response.getStatusCode();
        System.out.println("Deletion status: " + deletionStatus);
        
        return ResponseEntity.ok("order deleted successfully");
    }

    // ==================== EXCEPTION HANDLING DEMO - Approach 1 ====================
    // Using .onStatus() with lambda expressions
    // Creates an object of new DefaultResponseSpec() - Sets the lambda expression
    @GetMapping("/exception-handling-onStatus/{id}")
    public ResponseEntity<String> exceptionHandlingOnStatus(@PathVariable String id) {
        
        String response = restClient
                .get()
                .uri("http://localhost:8082/products/" + id)
                .retrieve()
                .onStatus(status -> status.is4xxClientError(), (request, resp) -> {
                    if (resp.getStatusCode().value() == 404) {
                        throw new MyCustomException("Invalid request passed");
                    }
                })
                .onStatus(status -> status.is5xxServerError(), (request, resp) -> {
                    throw new ServerException("Something wrong at server");
                })
                .body(String.class);
        
        System.out.println("Response from Product API called from order service: " + response);
        return ResponseEntity.ok("order call successful");
    }

    // ==================== EXCEPTION HANDLING DEMO - Approach 2 ====================
    // Using .exchange() for full control over Response building and Exception handling
    @GetMapping("/exception-handling-exchange/{id}")
    public ResponseEntity<String> exceptionHandlingExchange(@PathVariable String id) {
        
        String response = restClient
                .get()
                .uri("http://localhost:8082/products/" + id)
                .exchange((request, resp) -> {
                    if (resp.getStatusCode().is4xxClientError()) {
                        throw new MyCustomException("Invalid request passed");
                    } else if (resp.getStatusCode().is5xxServerError()) {
                        throw new InternalError("Something wrong at server");
                    } else {
                        // Response mapping logic, if there is no error
                        return StreamUtils.copyToString(resp.getBody(), StandardCharsets.UTF_8);
                    }
                });
        
        System.out.println("Response from Product API called from order service: " + response);
        return ResponseEntity.ok("order call successful");
    }
}

