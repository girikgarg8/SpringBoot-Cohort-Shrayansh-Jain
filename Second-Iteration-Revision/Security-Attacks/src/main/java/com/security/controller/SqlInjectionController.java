package com.security.controller;

import com.security.model.UserDetails;
import com.security.repository.UserDetailsRepository;
import com.security.service.SqlInjectionService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sql-injection")
public class SqlInjectionController {

    @Autowired
    private SqlInjectionService sqlInjectionService;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    // Initialize some test data
    @PostConstruct
    public void init() {
        userDetailsRepository.save(new UserDetails(null, "AA", "111"));
        userDetailsRepository.save(new UserDetails(null, "BB", "222"));
        System.out.println("✓ Test data initialized: Users AA and BB");
    }

    @GetMapping("/find")
    public List<UserDetails> findUser(@RequestParam String name) {
        System.out.println("\n==============================================");
        System.out.println("SQL Injection Endpoint Called");
        System.out.println("==============================================");
        
        List<UserDetails> results = sqlInjectionService.findByName(name);
        
        System.out.println("Results found: " + results.size());
        results.forEach(user -> 
            System.out.println("  → " + user.getName() + " (Phone: " + user.getPhone() + ")")
        );
        System.out.println("==============================================\n");
        
        return results;
    }
}

