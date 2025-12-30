package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    
    @Autowired
    private UserLoginEntityService userLoginEntityService;
    
    @PreAuthorize("#id == authentication.principal.id")
    @GetMapping("/{id}")
    public UserLoginEntity fetchUserDetails(@PathVariable Long id) {
        return (UserLoginEntity) userLoginEntityService.loadUserByUsername(
            userLoginEntityService.findById(id).getUsername()
        );
    }
}

