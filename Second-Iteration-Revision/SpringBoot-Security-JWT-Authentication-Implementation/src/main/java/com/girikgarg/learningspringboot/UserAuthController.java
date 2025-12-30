package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    
    @Autowired
    private UserAuthEntityService userAuthEntityService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserAuthEntity userAuth) {
        userAuth.setPassword(passwordEncoder.encode(userAuth.getPassword()));
        userAuthEntityService.save(userAuth);
        return ResponseEntity.ok("User registered successfully");
    }
}

