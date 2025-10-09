package com.girikgarg.learningspringboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")

public class ProductController {
    @GetMapping("/{id}")
    public String getProduct(@PathVariable("id") String id) {
        return "Product fetched with Id: " + id;
    }
}