package com.concepts.service;

import com.concepts.client.ProductClient;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class OrderService {

    @Autowired
    ProductClient productClient;

    // ==================== THREADPOOL BULKHEAD ====================
    // The AOP proxy intercepts your method call.
    // It submits your method to the Bulkhead's thread pool (configured via application.properties).
    // CompletableFuture.supplyAsync(() -> {
    //     return ourMethodLogic(); // The whole method body runs here
    // }, bulkheadThreadPoolExecutor);
    
    // It returns a CompletableFuture to the caller for async response.
    // Inside your method, CompletableFuture.completedFuture(...) just wraps
    // the result in a completed future — it does NOT run anything in a thread pool
    @Bulkhead(name = "productServiceThreadPool", type = Bulkhead.Type.THREADPOOL, fallbackMethod = "productThreadPoolFallback")
    public CompletableFuture<String> invokeProductAPIThreadPool(String id) {
        // Log the thread name to see which thread pool thread is executing this
        System.out.println("[ThreadPool] Thread name is: " + Thread.currentThread().getName());
        
        String response = productClient.getProductById(id);
        System.out.println("[ThreadPool] Response from Product api call is: " + response);
        
        return CompletableFuture.completedFuture(response);
    }

    // Even though it's async, we need to properly handle the exception
    // Else our client will get 500 response with BulkheadFullException
    public CompletableFuture<String> productThreadPoolFallback(String id, Throwable t) {
        System.out.println("[ThreadPool] Product Service is busy - ThreadPool + Queue full");
        return CompletableFuture.completedFuture("Product Service is busy - ThreadPool + Queue full");
    }

    // ==================== SEMAPHORE BULKHEAD ====================
    // Semaphore-based bulkhead: Lightweight, no separate thread pool
    // Uses permit-based concurrency control
    // Executes on the calling thread (not a separate thread pool)
    // If no permits available, fails immediately (based on maxWaitDuration)
    @Bulkhead(name = "productServiceSemaphore", type = Bulkhead.Type.SEMAPHORE, fallbackMethod = "productSemaphoreFallback")
    public String invokeProductAPISemaphore(String id) {
        System.out.println("[Semaphore] Thread name is: " + Thread.currentThread().getName());
        
        String response = productClient.getProductById(id);
        System.out.println("[Semaphore] Response from Product api call is: " + response);
        
        return response;
    }

    public String productSemaphoreFallback(String id, Throwable t) {
        System.out.println("[Semaphore] Too many concurrent requests, please try again later");
        return "Too many concurrent requests, please try again later";
    }
}

