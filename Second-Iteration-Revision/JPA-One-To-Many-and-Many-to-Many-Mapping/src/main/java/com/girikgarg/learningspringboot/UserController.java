package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{userId}/orders")
    public User removeFirstOrderFromUser(@PathVariable Long userId) {
        return userService.testOrphanRemoval(userId);
    }

    // ❌ Demo: Adding order the WRONG way (only setting inverse side)
    // Result: order.user_id_owing_fk will be NULL in database!
    @PostMapping("/{userId}/orders/wrong")
    public User addOrderWrongWay(@PathVariable Long userId, @RequestBody Order order) {
        return userService.addOrderWrongWay(userId, order.getProductName());
    }

    // ✅ Demo: Adding order the CORRECT way (setting both sides)
    // Result: order.user_id_owing_fk will be properly set in database!
    @PostMapping("/{userId}/orders/correct")
    public User addOrderCorrectWay(@PathVariable Long userId, @RequestBody Order order) {
        return userService.addOrderCorrectWay(userId, order.getProductName());
    }
}

