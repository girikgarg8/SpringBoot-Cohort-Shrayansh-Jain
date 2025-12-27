package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping(value = "/api/")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/test-jpa")
    public List<User> getUser() {
        User user = new User("xyz", "xyz@abc.com");
        userService.save(user);
        return userService.getUsers();
    }

    @GetMapping(path = "/user-by-id")
    public User getUserById() {
        User user = new User("abc", "def@abc.com");
        userService.save(user);
        return userService.getUserById(1L);
    }
}
