package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User create(User user) {
        return userRepository.save(user);
    }

    public Page<User> findByName(String name, Pageable pageable) {
        return userRepository.findByName(name, pageable);
    }

    @Transactional
    public void deleteByName(String name) {
        userRepository.deleteByName(name);
    }

    public User findByIdOrName(Long id, String name) {
        return userRepository.findByIdOrName(id, name);
    }

    public List<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<UserDTO> findUserDetailsWithAadhar(String username) {
        return userRepository.findUserDetailsWithAadhar(username);
    }

    public List<User> findUserWithAddressByName(String username) {
        return userRepository.findUserWithAddressByName(username);
    }

    @Transactional
    public void deleteUserByQuery(String username) {
        userRepository.deleteUser(username);
    }

    @Transactional
    public void deleteByUsername(String username) {
        userRepository.findById(1L).get();
        userRepository.deleteUser(username);
        Optional<User> output = userRepository.findById(1L);
        System.out.println("output present: " + output.isPresent());
    }
}


