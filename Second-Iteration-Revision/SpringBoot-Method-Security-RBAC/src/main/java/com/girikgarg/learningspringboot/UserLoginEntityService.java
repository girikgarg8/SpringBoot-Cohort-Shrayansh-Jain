package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserLoginEntityService implements UserDetailsService {
    
    @Autowired
    private UserLoginEntityRepository userLoginEntityRepository;
    
    public UserLoginEntity save(UserLoginEntity userLoginEntity) {
        return userLoginEntityRepository.save(userLoginEntity);
    }
    
    public UserLoginEntity findById(Long id) {
        return userLoginEntityRepository.findById(id)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userLoginEntityRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

