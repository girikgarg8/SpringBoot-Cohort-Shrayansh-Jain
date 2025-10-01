package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    /*
    curl -X POST http://localhost:8080/api/users \
  -H 'Content-Type: application/json' \
  -d '{"name":"Alice"}'
     */

    @PostMapping
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

    /*
    curl --location 'http://localhost:8080/api/users/by-name/Alice'
     */

    @GetMapping("/by-name/{name}")
    public Page<User> getByName(@PathVariable String name,
                                @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        Sort sort = Sort.by(Sort.Order.asc("id"), Sort.Order.desc( "name"));
        Pageable pageable = PageRequest.of(page, size, sort);
        return userService.findByName(name, pageable);
    }
    /*
    curl --location 'http://localhost:8080/api/users/by-username/Alice'
     */
    @GetMapping("/by-username/{name}")
    public java.util.List<User> getByUsername(@PathVariable("name") String username) {
        return userService.findByUsername(username);
    }

    /*
    curl --location 'http://localhost:8080/api/users/by-username-with-aadhar/Alice'
     */
    @GetMapping("/by-username-with-aadhar/{name}")
    public java.util.List<UserDTO> getByUsernameWithAadhar(@PathVariable("name") String username) {
        return userService.findUserDetailsWithAadhar(username);
    }

    /*
    curl --location 'http://localhost:8080/api/users/by-username-with-address/Alice'
     */
    @GetMapping("/by-username-with-address/{name}")
    public java.util.List<User> getByUsernameWithAddress(@PathVariable("name") String username) {
        return userService.findUserWithAddressByName(username);
    }

    /*
    curl -X DELETE 'http://localhost:8080/api/users/delete-with-transaction/Alice'
     */
    @DeleteMapping("/delete-with-transaction/{name}")
    public void deleteWithTransaction(@PathVariable("name") String username) {
        userService.deleteByUsername(username);
    }
    /*
    curl -X DELETE 'http://localhost:8080/api/users/delete-jpql/Alice'
     */
    @DeleteMapping("/delete-jpql/{name}")
    public void deleteByJpql(@PathVariable("name") String username) {
        userService.deleteUserByQuery(username);
    }

    /*
    curl -X DELETE http://localhost:8080/api/users/by-name/Alice
    */
    @DeleteMapping("/by-name/{name}")
    public void deleteByName(@PathVariable String name) {
        userService.deleteByName(name);
    }

    /*
    curl --location 'http://localhost:8080/api/users/by-id-or-name?id=1&name=Bob'
     */

    @GetMapping("/by-id-or-name")
    public User getByIdOrName(@RequestParam Long id, @RequestParam String name) {
        return userService.findByIdOrName(id, name);
    }
}


