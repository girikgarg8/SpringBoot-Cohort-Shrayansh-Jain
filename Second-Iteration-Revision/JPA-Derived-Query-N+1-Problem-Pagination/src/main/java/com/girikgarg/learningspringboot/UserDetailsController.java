package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserDetailsController {
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    // Create user
    @PostMapping
    public UserDetails createUser(@RequestBody UserDetails userDetails) {
        return userDetailsService.saveUser(userDetails);
    }
    
    // Bulk create users
    @PostMapping("/bulk")
    public List<UserDetails> createUsers(@RequestBody List<UserDetails> users) {
        return userDetailsService.saveAllUsers(users);
    }
    
    // Get all users
    @GetMapping
    public List<UserDetails> getAllUsers() {
        return userDetailsService.getAllUsers();
    }
    
    // Derived Query: findByName
    @GetMapping("/by-name")
    public List<UserDetails> findByName(@RequestParam String name) {
        return userDetailsService.findByName(name);
    }
    
    // Derived Query: findByNameAndPhone (AND condition)
    @GetMapping("/by-name-and-phone")
    public List<UserDetails> findByNameAndPhone(
            @RequestParam String name,
            @RequestParam String phone) {
        return userDetailsService.findByNameAndPhone(name, phone);
    }
    
    // Derived Query: findByNameAndPhoneOrUserId (AND + OR condition)
    @GetMapping("/by-name-and-phone-or-id")
    public List<UserDetails> findByNameAndPhoneOrUserId(
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam Long id) {
        return userDetailsService.findByNameAndPhoneOrUserId(name, phone, id);
    }
    
    // Derived Query: findByNameIsIn (IN clause)
    @GetMapping("/by-name-in")
    public List<UserDetails> findByNameIsIn(@RequestParam List<String> names) {
        return userDetailsService.findByNameIsIn(names);
    }
    
    // Derived Query: findByNameLike (LIKE clause)
    @GetMapping("/by-name-like")
    public List<UserDetails> findByNameLike(@RequestParam String pattern) {
        return userDetailsService.findByNameLike(pattern);
    }
    
    // Derived Query: findByUserIdBetween (BETWEEN clause)
    @GetMapping("/by-id-between")
    public List<UserDetails> findByUserIdBetween(
            @RequestParam Long startId,
            @RequestParam Long endId) {
        return userDetailsService.findByUserIdBetween(startId, endId);
    }
    
    // Derived Query: findByUserIdLessThan
    @GetMapping("/by-id-less-than")
    public List<UserDetails> findByUserIdLessThan(@RequestParam Long id) {
        return userDetailsService.findByUserIdLessThan(id);
    }
    
    // Derived Query: findByUserIdGreaterThan
    @GetMapping("/by-id-greater-than")
    public List<UserDetails> findByUserIdGreaterThan(@RequestParam Long id) {
        return userDetailsService.findByUserIdGreaterThan(id);
    }
    
    // Derived Query: findByPhoneIsNull
    @GetMapping("/by-phone-null")
    public List<UserDetails> findByPhoneIsNull() {
        return userDetailsService.findByPhoneIsNull();
    }
    
    // Derived Query: findByPhoneIsNotNull
    @GetMapping("/by-phone-not-null")
    public List<UserDetails> findByPhoneIsNotNull() {
        return userDetailsService.findByPhoneIsNotNull();
    }
    
    // Derived Query: findByNameStartingWith
    @GetMapping("/by-name-starting-with")
    public List<UserDetails> findByNameStartingWith(@RequestParam String prefix) {
        return userDetailsService.findByNameStartingWith(prefix);
    }
    
    // Derived Query: findByNameEndingWith
    @GetMapping("/by-name-ending-with")
    public List<UserDetails> findByNameEndingWith(@RequestParam String suffix) {
        return userDetailsService.findByNameEndingWith(suffix);
    }
    
    // Derived Query: findByNameContaining
    @GetMapping("/by-name-containing")
    public List<UserDetails> findByNameContaining(@RequestParam String substring) {
        return userDetailsService.findByNameContaining(substring);
    }
    
    // Derived Query: deleteByName (requires @Transactional)
    @DeleteMapping("/by-name")
    public String deleteByName(@RequestParam String name) {
        userDetailsService.deleteByName(name);
        return "Deleted users with name: " + name;
    }
    
    // Derived Query: deleteByUserId (returns count of deleted records)
    @DeleteMapping("/by-id")
    public String deleteByUserId(@RequestParam Long id) {
        Long count = userDetailsService.deleteByUserId(id);
        return "Deleted " + count + " user(s)";
    }
    
    // PAGINATION: Page 0, 5 records per page
    @GetMapping("/paginated")
    public Page<UserDetails> findByNamePaginated(@RequestParam String name) {
        return userDetailsService.findByNameStartingWithPaginated(name);
    }
    
    // SORTING: Sort by name descending
    @GetMapping("/sorted-desc")
    public List<UserDetails> findByNameSortedDesc(@RequestParam String name) {
        return userDetailsService.findByNameStartingWithSortedDesc(name);
    }
    
    // SORTING: Sort by name ascending
    @GetMapping("/sorted-asc")
    public List<UserDetails> findByNameSortedAsc(@RequestParam String name) {
        return userDetailsService.findByNameStartingWithSortedAsc(name);
    }
    
    // SORTING: Multiple fields - name ASC, phone DESC
    @GetMapping("/multi-sort")
    public List<UserDetails> findByNameMultiSort(@RequestParam String name) {
        return userDetailsService.findByNameStartingWithMultiSort(name);
    }
    
    // PAGINATION + SORTING: Custom page, size, and sort
    @GetMapping("/paginated-sorted")
    public Page<UserDetails> findByNamePaginatedAndSorted(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "userId") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return userDetailsService.findByNameWithPaginationAndSorting(name, page, size, sortBy, direction);
    }
    
    // ========== ADDITIONAL PAGINATION/SORTING ENDPOINTS ==========
    
    // FindByNameContaining with Pagination
    @GetMapping("/by-name-containing-paginated")
    public Page<UserDetails> findByNameContainingPaginated(
            @RequestParam String substring,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return userDetailsService.findByNameContainingPaginated(substring, page, size);
    }
    
    // FindByNameContaining with Sorting
    @GetMapping("/by-name-containing-sorted")
    public List<UserDetails> findByNameContainingSorted(
            @RequestParam String substring,
            @RequestParam(defaultValue = "name") String sortBy) {
        return userDetailsService.findByNameContainingSorted(substring, sortBy);
    }
    
    // FindByName (exact) with Pagination
    @GetMapping("/by-name-exact-paginated")
    public Page<UserDetails> findByNameExactPaginated(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return userDetailsService.findByNameExactPaginated(name, page, size);
    }
    
    // FindByNameEndingWith with Multi-field Sort
    @GetMapping("/by-name-ending-multi-sort")
    public List<UserDetails> findByNameEndingWithMultiSort(@RequestParam String suffix) {
        return userDetailsService.findByNameEndingWithMultiSort(suffix);
    }
    
    // Complex AND query with Pagination
    @GetMapping("/by-name-and-phone-paginated")
    public Page<UserDetails> findByNameAndPhonePaginated(
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        return userDetailsService.findByNameAndPhonePaginated(name, phone, page, size);
    }
    
    // Between with Pagination
    @GetMapping("/by-id-between-paginated")
    public Page<UserDetails> findByIdBetweenPaginated(
            @RequestParam Long startId,
            @RequestParam Long endId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        return userDetailsService.findByIdBetweenPaginated(startId, endId, page, size);
    }
    
    // GreaterThan with Sorting
    @GetMapping("/by-id-greater-sorted")
    public List<UserDetails> findByIdGreaterThanSorted(
            @RequestParam Long id,
            @RequestParam(defaultValue = "userId") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        return userDetailsService.findByIdGreaterThanSorted(id, sortBy, direction);
    }
    
    // IsNotNull with Pagination and Sorting
    @GetMapping("/by-phone-not-null-paginated")
    public Page<UserDetails> findByPhoneNotNullPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy) {
        return userDetailsService.findByPhoneNotNullPaginated(page, size, sortBy);
    }
    
    // ========== JPQL QUERY EXAMPLE ==========
    
    // Custom JPQL query using @Query annotation
    @GetMapping("/by-username-jpql")
    public List<UserDetails> findByUserNameJPQL(@RequestParam String userName) {
        return userDetailsService.findByUserNameJPQL(userName);
    }
    
    // ========== JPQL QUERIES WITH JOIN ==========
    
    // 1. JOIN query returning entire UserDetails with UserAddress
    @GetMapping("/with-address")
    public List<UserDetails> findUserDetailsWithAddress(@RequestParam String userName) {
        return userDetailsService.findUserDetailsWithAddress(userName);
    }
    
    // 2. JOIN query returning Object[] converted to DTO
    @GetMapping("/with-address-dto-object-array")
    public List<UserDTO> findByNameDerivedWithObjectArray(@RequestParam String name) {
        return userDetailsService.findByNameDerivedWithObjectArray(name);
    }
    
    // 3. JOIN query with constructor expression - directly returns DTO
    @GetMapping("/with-address-dto-constructor")
    public List<UserDTO> findByNameDerivedWithDTO(@RequestParam String name) {
        return userDetailsService.findByNameDerivedWithDTO(name);
    }
    
    // ============== WRITE OPERATIONS DEMO ==============
    
    @DeleteMapping("/delete-incorrect")
    public String deleteByUserNameIncorrect(@RequestParam String userName) {
        try {
            userDetailsService.deleteByUserNameIncorrect(userName);
            return "Deleted";
        } catch (Exception e) {
            return "ERROR: " + e.getClass().getSimpleName();
        }
    }
    
    @DeleteMapping("/delete-correct")
    public String deleteByUserNameCorrect(@RequestParam String userName) {
        int count = userDetailsService.deleteByUserNameCorrect(userName);
        return "Deleted: " + count;
    }
    
    @DeleteMapping("/delete-with-clear")
    public String deleteByUserNameWithClearCache(@RequestParam String userName) {
        int count = userDetailsService.deleteByUserNameWithClearCache(userName);
        return "Deleted with cache clear: " + count;
    }
    
    @PutMapping("/update-phone")
    public String updatePhoneByUserName(@RequestParam String userName, @RequestParam String newPhone) {
        int count = userDetailsService.updatePhoneByUserName(userName, newPhone);
        return "Updated: " + count;
    }
    
    // ============== L1 CACHE ISSUE DEMONSTRATION ==============
    
    @GetMapping("/demo-cache-issue")
    public Map<String, Object> demonstrateCacheIssue(@RequestParam String userName) {
        return userDetailsService.demonstrateCacheIssue(userName);
    }
    
    @GetMapping("/demo-cache-solved")
    public Map<String, Object> demonstrateCacheIssueSolved(@RequestParam String userName) {
        return userDetailsService.demonstrateCacheIssueSolved(userName);
    }
    
    // ============== NAMED QUERIES ==============
    
    @GetMapping("/by-name-named-query")
    public List<UserDetails> findUserByNameNamedQuery(@RequestParam String name) {
        return userDetailsService.findUserByNameNamedQuery(name);
    }
    
    @GetMapping("/by-phone-named-query")
    public List<UserDetails> findUserByPhoneNamedQuery(@RequestParam String phone) {
        return userDetailsService.findUserByPhoneNamedQuery(phone);
    }
}

