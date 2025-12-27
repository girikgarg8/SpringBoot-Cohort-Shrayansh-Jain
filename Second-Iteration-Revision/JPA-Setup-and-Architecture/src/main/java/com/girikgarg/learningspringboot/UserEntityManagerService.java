package com.girikgarg.learningspringboot;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserEntityManagerService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    // ❌ WITHOUT @Transactional - Will throw TransactionRequiredException!
    public void saveUserWithoutTransaction(User user) {
        // This will FAIL with: 
        // "No EntityManager with actual transaction available for current thread"
        entityManager.persist(user);
        // Exception thrown: TransactionRequiredException
    }
    
    // ✅ WITH @Transactional - Works fine!
    @Transactional
    public void saveUserWithTransaction(User user) {
        // This WORKS because @Transactional:
        // 1. Begins a transaction
        // 2. Creates/binds EntityManager to thread
        // 3. Executes this method
        // 4. Commits transaction
        // 5. Flushes changes to DB
        entityManager.persist(user);
    }
    
    // ✅ READ without @Transactional - Works fine!
    public List<User> getAllUsersWithoutTransaction() {
        // This WORKS even without @Transactional
        return entityManager.createQuery("SELECT u FROM User u", User.class)
                           .getResultList();
    }
}

