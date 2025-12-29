package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserDetailsController {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @PostMapping
    public UserDetails createUser(@RequestBody UserDetails userDetails) {
        return userDetailsService.saveUser(userDetails);
    }
    
    @GetMapping("/{id}")
    public UserDetails getUser(@PathVariable Long id) {
        return userDetailsService.getUser(id).orElse(null);
    }
    
    @GetMapping
    public List<UserDetails> getAllUsers() {
        return userDetailsService.getAllUsers();
    }
    
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userDetailsService.deleteUser(id);
    }
    
    // Native Query with all fields - works fine
    @GetMapping("/native-all-fields")
    public List<UserDetails> getUsersByNameNative(@RequestParam String name) {
        return userDetailsService.getUsersByNameNative(name);
    }
    
    // Native Query with partial fields - WILL THROW ERROR
    @GetMapping("/native-partial-error")
    public List<UserDetails> getUsersByNameNativePartial(@RequestParam String name) {
        return userDetailsService.getUsersByNameNativePartial(name);
    }
    
    // Solution 1: Using @SqlResultSetMapping with @NamedNativeQuery
    @GetMapping("/native-with-mapping")
    public List<UserDTO> getUsersByNameWithMapping(@RequestParam String name) {
        return userDetailsService.getUsersByNameWithMapping(name);
    }
    
    // Solution 2: Manual mapping from Object[] to DTO
    @GetMapping("/native-manual-mapping")
    public List<UserDTO> getUsersByNameManualMapping(@RequestParam String name) {
        return userDetailsService.getUsersByNameManualMapping(name);
    }
    
    // Dynamic Native Query with EntityManager
    @GetMapping("/dynamic-native-query")
    public List<UserDTOExtended> getUserDetailsByNameDynamic(@RequestParam(required = false) String userName) {
        return userDetailsService.getUserDetailsByNameNativeQuery(userName);
    }
    
    // Way 1: Manual Pagination and Sorting with EntityManager
    @GetMapping("/native-paginated-manual")
    public List<UserDTOExtended> getUserDetailsByNamePaginatedManual(
            @RequestParam(required = false) String userName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return userDetailsService.getUserDetailsByNameNativeQueryPaginated(userName, page, size);
    }
    
    // Way 2: Using Spring Data JPA Pageable with Native Query
    @GetMapping("/native-paginated-spring")
    public List<UserDetails> getUserDetailsByNamePaginatedSpring(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return userDetailsService.getUserDetailsByNameNativeQueryPageable(name, page, size);
    }
    
    // Criteria API
    @GetMapping("/criteria-api")
    public List<UserDetails> getUserDetailsByPhoneCriteriaAPI(@RequestParam String phoneNo) {
        return userDetailsService.getUserDetailsByPhoneCriteriaAPI(phoneNo);
    }
    
    // Criteria API - Select Multiple Fields
    @GetMapping("/criteria-api-multiselect")
    public List<UserDTO> getUserDetailsByPhoneCriteriaAPIMultiSelect(@RequestParam String phoneNo) {
        return userDetailsService.getUserDetailsByPhoneCriteriaAPIMultiSelect(phoneNo);
    }
    
    // Criteria API - Join
    @GetMapping("/criteria-api-join")
    public List<UserDTO> getUserDetailsByPhoneCriteriaAPIJoin(@RequestParam String phoneNo) {
        return userDetailsService.getUserDetailsByPhoneCriteriaAPIJoin(phoneNo);
    }
    
    // Criteria API - Pagination and Sorting
    @GetMapping("/criteria-api-paginated")
    public List<UserDetails> getUserDetailsByPhoneCriteriaAPIPaginated(
            @RequestParam String phoneNo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return userDetailsService.getUserDetailsByPhoneCriteriaAPIPaginated(phoneNo, page, size);
    }
}

