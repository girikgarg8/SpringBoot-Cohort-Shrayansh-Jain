package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserDetailsController {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @PostMapping
    public UserDetails createUser(@RequestBody UserDetails userDetails) {
        return userDetailsService.saveUser(userDetails);
    }
    
    @GetMapping("/{id}")
    public UserDetails getUser(@PathVariable Long id) {
        return userDetailsService.getUser(id).orElse(null);
    }
    
    @GetMapping
    public List<UserDetails> getAllUsers() {
        return userDetailsService.getAllUsers();
    }
    
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userDetailsService.deleteUser(id);
    }
    
    @GetMapping("/specification-by-phone")
    public List<UserDetails> getUserDetailsByPhoneSpecificationAPI(@RequestParam String phoneNo) {
        return userDetailsService.getUserDetailsByPhoneSpecificationAPI(phoneNo);
    }
    
    @GetMapping("/specification-by-name")
    public List<UserDetails> getUserDetailsByNameSpecificationAPI(@RequestParam String name) {
        return userDetailsService.getUserDetailsByNameSpecificationAPI(name);
    }
    
    @GetMapping("/specification-by-phone-and-name")
    public List<UserDetails> getUserDetailsByPhoneAndNameSpecificationAPI(
            @RequestParam String phoneNo, 
            @RequestParam String name) {
        return userDetailsService.getUserDetailsByPhoneAndNameSpecificationAPI(phoneNo, name);
    }
}

