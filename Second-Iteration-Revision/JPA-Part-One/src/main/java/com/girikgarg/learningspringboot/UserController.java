package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class UserController {
    // Old approach - Direct JDBC using DAO
    private final UserDAO userDAO = new UserDAO();
    
    // New approach - Spring JDBC using Service layer
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ============ OLD APPROACH (Direct JDBC) ============
    
    @PostMapping("/create-table")
    public String createTable() {
        userDAO.createUserTable();
        return "User table created";
    }

    @PostMapping("/create")
    public String createUser(@RequestParam String name, @RequestParam int age) {
        userDAO.createUser(name, age);
        return "User created";
    }

    @GetMapping
    public String readUsers() {
        userDAO.readUsers();
        return "Users printed to console";
    }

    // ============ NEW APPROACH (Spring JDBC) ============
    
    @PostMapping("/spring-jdbc/create-table")
    public String createTableWithSpringJdbc() {
        userService.createTable();
        return "User table created using Spring JDBC";
    }

    @PostMapping("/spring-jdbc/users")
    public String createUserWithSpringJdbc(@RequestParam String name, @RequestParam int age) {
        userService.insertUser(name, age);
        return "User created using Spring JDBC";
    }

    @GetMapping("/spring-jdbc/users")
    public List<User> getUsersWithSpringJdbc() {
        return userService.getUsers();
    }
}
