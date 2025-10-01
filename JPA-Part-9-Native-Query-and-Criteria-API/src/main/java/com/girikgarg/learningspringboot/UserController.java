package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/native/users")
public class UserController {
    @Autowired
    private UserService userService;

    /*
    curl -X POST http://localhost:8080/api/native/users \
      -H 'Content-Type: application/json' \
      -d '{"name":"Alice"}'
     */
    @PostMapping
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    /*
    curl --location 'http://localhost:8080/api/native/users/by-username/Alice'
     */
    @GetMapping("/by-username/{name}")
    public UserDTO getByUsername(@PathVariable("name") String username) {
        return userService.findByUsernameWithNativeQuery(username);
    }

    @GetMapping("/{name}")
    public List<UserDTO> getByName(@PathVariable("name") String username) {
        return userService.getUserDetailsByNameFromNativeQuery(username);
    }

    /*
    curl --location 'http://localhost:8080/api/native/users/by-name-native/Alice'
     */
    @GetMapping("/by-name-native/{name}")
    public List<UserDTO> getByNameNative(@PathVariable("name") String username) {
        return userService.getUserDetailsByNameNativeQuery(username);
    }

    /*
    curl --location 'http://localhost:8080/api/native/users/by-name-criteria/Alice'
     */
    @GetMapping("/by-name-criteria/{name}")
    public List<User> getByNameCriteria(@PathVariable("name") String username) {
        return userService.getUserDetailsByName(username);
    }

    /*
    curl --location 'http://localhost:8080/api/native/users/by-name-criteria-dto/Alice'
     */
    @GetMapping("/by-name-criteria-dto/{name}")
    public List<UserDTO> getByNameCriteriaDto(@PathVariable("name") String username) {
        return userService.findUserDetailsByName(username);
    }

    /*
    curl --location 'http://localhost:8080/api/native/users/by-name-spec/Alice'
     */
    @GetMapping("/by-name-spec/{name}")
    public List<User> getByNameSpec(@PathVariable("name") String username) {
        return userService.getUserDetailsByNameWithSpecification(username);
    }

    /*
    curl --location 'http://localhost:8080/api/native/users/by-name-spec2?name=Alice&id=1'
     */
    @GetMapping("/by-name-spec2")
    public List<User> getByNameSpec2(@RequestParam("name") String name,
                                     @RequestParam("id") Long id) {
        return userService.getUserDetailsByNameSpecification(name, id);
    }
}
