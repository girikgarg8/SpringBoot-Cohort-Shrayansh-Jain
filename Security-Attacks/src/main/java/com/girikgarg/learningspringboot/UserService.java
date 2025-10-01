package com.girikgarg.learningspringboot;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> findByName(String name) {
        String sql = "SELECT * FROM USERS WHERE name= '" + name + "'";
        return entityManager.createNativeQuery(sql, User.class).getResultList();
    }
}
