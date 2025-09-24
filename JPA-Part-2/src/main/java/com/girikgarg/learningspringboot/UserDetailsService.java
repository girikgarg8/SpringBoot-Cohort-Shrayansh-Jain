package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    public void saveUser(UserDetails userDetails) {
        userDetailsRepository.save(userDetails);
    }

    public List<UserDetails> getAllUsers() {
        return userDetailsRepository.findAll();
    }
}

