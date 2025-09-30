package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * curl --location 'http://localhost:8080/users' \
     * --header 'Content-Type: application/json' \
     * --data-raw '{
     *     "name" : "Girik",
     *     "aadhar": {
     *         "phone": "123456",
     *         "email": "abc@example.com"
     *     }
     * }'
     */

    @PostMapping()
    public User insertUser(@RequestBody User user) {
        return userService.save(user);
    }

    /**
     * curl --location --request PUT 'http://localhost:8080/users/1' \
     * --header 'Content-Type: application/json' \
     * --data-raw '{
     *     "id" : "1",
     *     "name" : "Girik_updated",
     *     "aadhar": {
     *         "id": 1,
     *         "phone": "123456",
     *         "email": "abc_def@example.com"
     *     }
     * }'
     */

    @PutMapping("/{id}")
    public User updateUser(@RequestBody User user) {
        return userService.update(user);
    }

    // curl --location --request DELETE 'http://localhost:8080/users/1'
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        return userService.findById(id);
    }
}
