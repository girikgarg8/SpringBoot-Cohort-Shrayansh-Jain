package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createTable() {
        userRepository.createTable();
    }

    public void insertUser(String name, int age) {
        userRepository.insertUser(name, age);
    }

    public List <User> getUsers() {
        List <User> users = userRepository.getUsers();
        for (User user: users) {
            System.out.println("User ID is: " + user.getId() + " and age is: "+ user.getAge());
        }

        return users;
    }
}
