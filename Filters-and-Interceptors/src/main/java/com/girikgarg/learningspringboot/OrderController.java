package com.girikgarg.learningspringboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @GetMapping("/orders/{id}")
    public String getOrders(@PathVariable int id) {
        System.out.println("Order Controller Executing");
        return "Hello World";
    }
}
