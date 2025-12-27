package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/transaction-demo")
public class TransactionDemoController {
    
    @Autowired
    private UserEntityManagerService userEntityManagerService;
    
    // ❌ DEMO 1: Write without @Transactional - WILL FAIL
    @GetMapping("/test-write-without-transaction")
    public Map<String, String> testWriteWithoutTransaction() {
        Map<String, String> response = new HashMap<>();
        try {
            User user = new User("TestUser", "test@example.com");
            userEntityManagerService.saveUserWithoutTransaction(user);
            response.put("status", "success");
            response.put("message", "User saved successfully (This shouldn't happen!)");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("error", e.getClass().getSimpleName());
            response.put("message", "Expected error: " + e.getMessage());
        }
        return response;
    }
    
    // ✅ DEMO 2: Write with @Transactional - WILL WORK
    @GetMapping("/test-write-with-transaction")
    public Map<String, String> testWriteWithTransaction() {
        Map<String, String> response = new HashMap<>();
        try {
            User user = new User("TransactionalUser", "transactional@example.com");
            userEntityManagerService.saveUserWithTransaction(user);
            response.put("status", "success");
            response.put("message", "User saved successfully with @Transactional!");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("error", e.getClass().getSimpleName());
            response.put("message", e.getMessage());
        }
        return response;
    }
    
    // ✅ DEMO 3: Read without @Transactional - WILL WORK
    @GetMapping("/test-read-without-transaction")
    public Map<String, Object> testReadWithoutTransaction() {
        Map<String, Object> response = new HashMap<>();
        try {
            // First create a user (with transaction)
            User user = new User("ReadTestUser", "read@example.com");
            userEntityManagerService.saveUserWithTransaction(user);
            
            // Now try to read without transaction
            List<User> users = userEntityManagerService.getAllUsersWithoutTransaction();
            response.put("status", "success");
            response.put("message", "Read works WITHOUT @Transactional!");
            response.put("userCount", users.size());
        } catch (Exception e) {
            response.put("status", "error");
            response.put("error", e.getClass().getSimpleName());
            response.put("message", e.getMessage());
        }
        return response;
    }
}

