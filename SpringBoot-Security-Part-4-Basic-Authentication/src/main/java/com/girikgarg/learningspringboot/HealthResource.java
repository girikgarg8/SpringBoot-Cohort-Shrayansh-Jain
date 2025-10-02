package com.girikgarg.learningspringboot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthResource {
    @GetMapping("/health/full")
    public ResponseEntity<String> getHealth() {
        return ResponseEntity.ok("Health Check OK");
    }
}
