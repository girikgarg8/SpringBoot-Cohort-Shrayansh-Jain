package com.girikgarg.learningspringboot;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserDAO userDAO = new UserDAO();
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create-table")
    public String createTable() {
        userDAO.createUserTable();
        return "User table created.";
    }

    @PostMapping("/create")
    public String createUser(@RequestParam String name, @RequestParam int age) {
        userDAO.createUser(name, age);
        return "User created.";
    }

    @GetMapping
    public String readUsers() {
        userDAO.readUsers();
        return "Users printed to console.";
    }

    @GetMapping("/demo")
    public List<User> demo() {
        userRepository.createTable();
        userRepository.insertUser("John", 30);
        return userRepository.getUsers();
    }
}
