package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
 * ANSWER: Why do we need to implement UserDetailsService?
 * 
 * UserDetailsService is the interface Spring Security uses to load user data.
 * It has ONE method: loadUserByUsername(String username)
 * 
 * When a user tries to log in:
 *   1. Spring Security calls loadUserByUsername() with the provided username
 *   2. We query the database to find the user
 *   3. We return the UserDetails object (our UserAuthEntity)
 *   4. Spring Security compares the passwords
 * 
 * By implementing this, we tell Spring Security:
 *   "Use THIS method to load users from OUR database"
 * instead of using in-memory users or default configuration.
 */
@Service
public class UserAuthEntityService implements UserDetailsService {
    
    @Autowired
    private UserAuthEntityRepository userAuthEntityRepository;
    
    public UserAuthEntity save(UserAuthEntity userAuth) {
        return userAuthEntityRepository.save(userAuth);
    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userAuthEntityRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
