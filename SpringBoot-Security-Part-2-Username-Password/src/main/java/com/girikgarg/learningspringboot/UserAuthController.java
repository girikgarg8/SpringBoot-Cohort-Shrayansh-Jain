package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class UserAuthController {

    @Autowired
    private UserAuthEntityService userAuthEntityService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    /**
     * curl --location 'http://localhost:8080/auth/register' \
     * --header 'Content-Type: application/json' \
     * --data '{
     *     "username": "abcdef",
     *     "password": "xyz",
     *     "role": "ADMIN"
     * }'
     */
    public ResponseEntity<String> register(@RequestBody UserAuthEntity userAuthEntity) {
        // hash the password before saving
        userAuthEntity.setPassword(passwordEncoder.encode(userAuthEntity.getPassword()));

        // save user
        userAuthEntityService.save(userAuthEntity);
        return ResponseEntity.ok("User registered successfully");
    }
}
