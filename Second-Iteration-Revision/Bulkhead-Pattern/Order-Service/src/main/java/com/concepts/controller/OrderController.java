package com.concepts.controller;

import com.concepts.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    // ==================== THREADPOOL BULKHEAD ENDPOINT ====================
    @GetMapping("/threadpool/{id}")
    public CompletableFuture<String> callProductAPIThreadPool(@PathVariable String id) {
        System.out.println("[ThreadPool] Request " + id + " received on thread: " + Thread.currentThread().getName());
        return orderService.invokeProductAPIThreadPool(id);
    }

    // ==================== SEMAPHORE BULKHEAD ENDPOINT ====================
    @GetMapping("/semaphore/{id}")
    public String callProductAPISemaphore(@PathVariable String id) {
        System.out.println("[Semaphore] Request " + id + " received on thread: " + Thread.currentThread().getName());
        return orderService.invokeProductAPISemaphore(id);
    }
}

