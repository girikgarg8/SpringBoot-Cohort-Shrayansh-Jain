package com.girikgarg.learningspringboot;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public void saveUser(UserDetails userDetails) {
        userDetailsRepository.save(userDetails);
    }

    public List<UserDetails> getAllUsers() {
        return userDetailsRepository.findAll();
    }

    public UserDetails findByName(String username) {
        return userDetailsRepository.findByName(username);
    }

    public Optional<UserDetails> findById(Long id) {
        return userDetailsRepository.findById(id);
    }

    @Transactional
    public void saveUser2(UserDetails userDetails) {
        entityManager.persist(userDetails);
    }

    public UserDetails testUser(UserDetails userDetails) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(userDetails);
        entityManager.find(UserDetails.class, 1L);
        UserDetails output = entityManager.find(UserDetails.class, 1L);
        System.out.println("I am able to find the data, name is: "+ output.getName());
        entityManager.getTransaction().commit();
        entityManager.close();

        EntityManager entityManager2 = entityManagerFactory.createEntityManager();
        entityManager2.getTransaction().begin();
        entityManager2.find(UserDetails.class, 1L);
        UserDetails output2 = entityManager2.find(UserDetails.class, 1L);
        System.out.println("Session2: I am able to find the data, name is: "+ output2.getName());
        entityManager2.getTransaction().commit();
        entityManager2.close();

        return output2;
    }

    @Transactional
    public UserDetails editEntity(Long id) {
        UserDetails user = entityManager.find(UserDetails.class, id);
        if (user == null) {
            throw new IllegalArgumentException("User with id=" + id + " not found");
        }
        String currentName = user.getName();
        user.setName(currentName == null ? "updated" : currentName + "_updated");
        entityManager.flush();
        return user;
    }
}

