package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserDetailsService {
    
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    
    public UserDetails saveUser(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }
    
    public Optional<UserDetails> getUser(Long id) {
        return userDetailsRepository.findById(id);
    }
    
    public List<UserDetails> getAllUsers() {
        return userDetailsRepository.findAll();
    }
    
    public void deleteUser(Long id) {
        userDetailsRepository.deleteById(id);
    }
    
    public List<UserDetails> getUserDetailsByPhoneSpecificationAPI(String phoneNo) {
        Specification<UserDetails> result = Specification.where(UserSpecification.joinAddress())
                .and(UserSpecification.equalsPhone(phoneNo));
        
        return userDetailsRepository.findAll(result);
    }
    
    public List<UserDetails> getUserDetailsByNameSpecificationAPI(String name) {
        Specification<UserDetails> result = Specification.where(UserSpecification.joinAddress())
                .and(UserSpecification.likeName(name));
        
        return userDetailsRepository.findAll(result);
    }
    
    public List<UserDetails> getUserDetailsByPhoneAndNameSpecificationAPI(String phoneNo, String name) {
        Specification<UserDetails> result = Specification.where(UserSpecification.joinAddress())
                .and(UserSpecification.equalsPhone(phoneNo))
                .and(UserSpecification.likeName(name));
        
        return userDetailsRepository.findAll(result);
    }
}

