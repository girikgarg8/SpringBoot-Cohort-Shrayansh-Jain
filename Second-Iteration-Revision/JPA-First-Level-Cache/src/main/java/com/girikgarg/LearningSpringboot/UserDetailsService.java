package com.girikgarg.learningspringboot;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsService {
    
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    
    @Autowired
    EntityManagerFactory entityManagerFactory;
    
    @Transactional
    public void saveUser(UserDetails userDetails) {
        userDetailsRepository.save(userDetails);
    }
    
    @Transactional(readOnly = true)
    public UserDetails getUser(Long primaryKey) {
        return userDetailsRepository.findById(primaryKey).orElse(null);
    }
    
    public UserDetails testSaveUser(UserDetails user) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.find(UserDetails.class, 1L);
        UserDetails output = entityManager.find(UserDetails.class, 1L);
        System.out.println("i am able to find the data, name is:" + output.getName());
        entityManager.getTransaction().commit();
        entityManager.close();
        
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        entityManager2.getTransaction().begin();
        entityManager2.find(UserDetails.class, 1L);
        UserDetails output2 = entityManager2.find(UserDetails.class, 1L);
        System.out.println("Session2: i am able to find the data, name is:" + output2.getName());
        entityManager2.getTransaction().commit();
        entityManager2.close();
        
        return output2;
    }
}

