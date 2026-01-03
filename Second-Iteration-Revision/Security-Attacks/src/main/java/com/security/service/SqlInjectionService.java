package com.security.service;

import com.security.model.UserDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SqlInjectionService {

    @PersistenceContext
    private EntityManager entityManager;

    // VULNERABLE: SQL Injection - directly concatenating user input into SQL query
    @SuppressWarnings("unchecked")
    public List<UserDetails> findByName(String name) {
        System.out.println("=== SQL Injection Demo ===");
        System.out.println("User input: " + name);
        
        // VULNERABLE CODE - DO NOT USE IN PRODUCTION
        String sql = "SELECT * FROM user_details WHERE name = '" + name + "'";
        System.out.println("Generated SQL: " + sql);
        
        return entityManager.createNativeQuery(sql, UserDetails.class).getResultList();
    }
}

