package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLoginController {
    
    @Autowired
    private UserLoginEntityService userLoginEntityService;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @PostMapping("/user-login")
    public ResponseEntity<String> login(@RequestBody UserLoginEntity userLoginEntity) {
        userLoginEntity.setPassword(passwordEncoder.encode(userLoginEntity.getPassword()));
        userLoginEntityService.save(userLoginEntity);
        return ResponseEntity.ok("User registered successfully");
    }
}

