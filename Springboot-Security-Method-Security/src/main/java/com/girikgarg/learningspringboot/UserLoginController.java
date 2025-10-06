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

    /**
     *
         curl --location 'localhost:8080/user/register' \
         --header 'Content-Type: application/json' \
         --data '{
         "username": "abc",
         "password": "abc123",
         "role": "ROLE_USER",
         "permissions": [
         {
         "name": "ORDER_READ"
         }
         ]
         }'
     */

    @PostMapping("/user/register")
    public ResponseEntity<String> login(@RequestBody UserLoginEntity userLoginEntity) {
        // hash the password before saving
        userLoginEntity.setPassword(passwordEncoder.encode(userLoginEntity.getPassword()));

        userLoginEntityService.save(userLoginEntity);
        return ResponseEntity.ok("User registered successfully");
    }

}
