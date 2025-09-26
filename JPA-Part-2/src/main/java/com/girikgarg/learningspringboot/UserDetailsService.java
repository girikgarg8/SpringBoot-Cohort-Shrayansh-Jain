package com.girikgarg.learningspringboot;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserDetailsService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public void saveUser(UserDetails userDetails) {
        userDetailsRepository.save(userDetails);
    }

    public List<UserDetails> getAllUsers() {
        return userDetailsRepository.findAll();
    }

    @Transactional
    public void saveUser2(UserDetails userDetails) {
        entityManager.persist(userDetails);
    }
}

