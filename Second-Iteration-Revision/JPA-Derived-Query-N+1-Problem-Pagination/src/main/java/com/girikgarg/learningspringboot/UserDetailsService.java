package com.girikgarg.learningspringboot;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserDetailsService {
    
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public UserDetails saveUser(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }
    
    public List<UserDetails> saveAllUsers(List<UserDetails> users) {
        return userDetailsRepository.saveAll(users);
    }
    
    public List<UserDetails> findByName(String userName) {
        return userDetailsRepository.findUserDetailsByName(userName);
    }
    
    public List<UserDetails> findByNameAndPhone(String userName, String phone) {
        return userDetailsRepository.findUserDetailsByNameAndPhone(userName, phone);
    }
    
    public List<UserDetails> findByNameAndPhoneOrUserId(String userName, String phone, Long id) {
        return userDetailsRepository.findUserDetailsByNameAndPhoneOrUserId(userName, phone, id);
    }
    
    public List<UserDetails> findByNameIsIn(List<String> userNames) {
        return userDetailsRepository.findUserDetailsByNameIsIn(userNames);
    }
    
    public List<UserDetails> findByNameLike(String pattern) {
        return userDetailsRepository.findUserDetailsByNameLike(pattern);
    }
    
    public List<UserDetails> findByUserIdBetween(Long startId, Long endId) {
        return userDetailsRepository.findUserDetailsByUserIdBetween(startId, endId);
    }
    
    public List<UserDetails> findByUserIdLessThan(Long userId) {
        return userDetailsRepository.findUserDetailsByUserIdLessThan(userId);
    }
    
    public List<UserDetails> findByUserIdGreaterThan(Long userId) {
        return userDetailsRepository.findUserDetailsByUserIdGreaterThan(userId);
    }
    
    public List<UserDetails> findByPhoneIsNull() {
        return userDetailsRepository.findUserDetailsByPhoneIsNull();
    }
    
    public List<UserDetails> findByPhoneIsNotNull() {
        return userDetailsRepository.findUserDetailsByPhoneIsNotNull();
    }
    
    public List<UserDetails> findByNameStartingWith(String prefix) {
        return userDetailsRepository.findUserDetailsByNameStartingWith(prefix);
    }
    
    public List<UserDetails> findByNameEndingWith(String suffix) {
        return userDetailsRepository.findUserDetailsByNameEndingWith(suffix);
    }
    
    public List<UserDetails> findByNameContaining(String substring) {
        return userDetailsRepository.findUserDetailsByNameContaining(substring);
    }
    
    @Transactional
    public void deleteByName(String userName) {
        userDetailsRepository.deleteByName(userName);
    }
    
    @Transactional
    public Long deleteByUserId(Long userId) {
        return userDetailsRepository.deleteByUserId(userId);
    }
    
    public List<UserDetails> getAllUsers() {
        return userDetailsRepository.findAll();
    }
    
    // PAGINATION: Returns Page with metadata
    public Page<UserDetails> findByNameStartingWithPaginated(String name) {
        // Page 0, 5 records per page
        Pageable pageable = PageRequest.of(0, 5);
        Page<UserDetails> userDetailsPage = userDetailsRepository.findUserDetailsByNameStartingWith(name, pageable);
        
        List<UserDetails> userDetailsList = userDetailsPage.getContent();
        System.out.println("total pages: " + userDetailsPage.getTotalPages());
        System.out.println("is first page: " + userDetailsPage.isFirst());
        System.out.println("is last page: " + userDetailsPage.isLast());
        
        return userDetailsPage;
    }
    
    // SORTING: Single field descending
    public List<UserDetails> findByNameStartingWithSortedDesc(String name) {
        return userDetailsRepository.findUserDetailsByNameStartingWith(name, Sort.by("name").descending());
    }
    
    // SORTING: Single field ascending
    public List<UserDetails> findByNameStartingWithSortedAsc(String name) {
        return userDetailsRepository.findUserDetailsByNameStartingWith(name, Sort.by("name").ascending());
    }
    
    // SORTING: Multiple fields with different sort orders
    // First sort by "name" ascending, then if duplicates, sort by "phone" descending
    public List<UserDetails> findByNameStartingWithMultiSort(String name) {
        Sort sort = Sort.by(
            Sort.Order.asc("name"),
            Sort.Order.desc("phone")
        );
        return userDetailsRepository.findUserDetailsByNameStartingWith(name, sort);
    }
    
    // PAGINATION + SORTING: Custom page number and size with sorting
    public Page<UserDetails> findByNameWithPaginationAndSorting(String name, int pageNumber, int pageSize, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return userDetailsRepository.findUserDetailsByNameStartingWith(name, pageable);
    }
    
    // ========== ADDITIONAL PAGINATION/SORTING EXAMPLES ==========
    
    // FindByNameContaining with Pagination
    public Page<UserDetails> findByNameContainingPaginated(String substring, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userDetailsRepository.findUserDetailsByNameContaining(substring, pageable);
    }
    
    // FindByNameContaining with Sorting
    public List<UserDetails> findByNameContainingSorted(String substring, String sortBy) {
        return userDetailsRepository.findUserDetailsByNameContaining(substring, Sort.by(sortBy));
    }
    
    // FindByName (exact match) with Pagination
    public Page<UserDetails> findByNameExactPaginated(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("userId").descending());
        return userDetailsRepository.findUserDetailsByName(name, pageable);
    }
    
    // FindByNameEndingWith with Sort (multiple fields)
    public List<UserDetails> findByNameEndingWithMultiSort(String suffix) {
        Sort sort = Sort.by(
            Sort.Order.asc("name"),
            Sort.Order.asc("phone")
        );
        return userDetailsRepository.findUserDetailsByNameEndingWith(suffix, sort);
    }
    
    // Complex AND query with Pagination
    public Page<UserDetails> findByNameAndPhonePaginated(String name, String phone, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userDetailsRepository.findUserDetailsByNameAndPhone(name, phone, pageable);
    }
    
    // Between query with Pagination
    public Page<UserDetails> findByIdBetweenPaginated(Long startId, Long endId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("userId"));
        return userDetailsRepository.findUserDetailsByUserIdBetween(startId, endId, pageable);
    }
    
    // GreaterThan with Sorting
    public List<UserDetails> findByIdGreaterThanSorted(Long id, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc") 
            ? Sort.by(sortBy).descending() 
            : Sort.by(sortBy).ascending();
        return userDetailsRepository.findUserDetailsByUserIdGreaterThan(id, sort);
    }
    
    // IsNotNull with Pagination and Sorting
    public Page<UserDetails> findByPhoneNotNullPaginated(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return userDetailsRepository.findUserDetailsByPhoneIsNotNull(pageable);
    }
    
    // ========== JPQL QUERY EXAMPLE ==========
    
    // Custom JPQL query using @Query annotation
    public List<UserDetails> findByUserNameJPQL(String userName) {
        return userDetailsRepository.findByUserName(userName);
    }
    
    // ========== JPQL QUERIES WITH JOIN ==========
    
    // 1. JOIN query returning entire entities
    public List<UserDetails> findUserDetailsWithAddress(String userName) {
        return userDetailsRepository.findUserDetailsWithAddress(userName);
    }
    
    // 2. JOIN query returning specific fields as Object[] - then convert to DTO
    public List<UserDTO> findByNameDerivedWithObjectArray(String name) {
        List<Object[]> dbOutput = userDetailsRepository.findUserDetailsWithAddressAsObjectArray(name);
        List<UserDTO> output = new ArrayList<>();
        
        for (Object[] val : dbOutput) {
            String userName = (String) val[0];
            String country = (String) val[1];
            UserDTO dto = new UserDTO(userName, country);
            output.add(dto);
        }
        
        return output;
    }
    
    // 3. JOIN query with constructor expression - directly returns DTO
    public List<UserDTO> findByNameDerivedWithDTO(String name) {
        return userDetailsRepository.findUserDetailsWithAddressAsDTO(name);
    }
    
    // ============== WRITE OPERATIONS DEMO ==============
    
    public void deleteByUserNameIncorrect(String userName) {
        userDetailsRepository.deleteByUserNameIncorrect(userName);
    }
    
    public int deleteByUserNameCorrect(String userName) {
        return userDetailsRepository.deleteByUserNameCorrect(userName);
    }
    
    public int deleteByUserNameWithClearCache(String userName) {
        return userDetailsRepository.deleteByUserNameWithClearCache(userName);
    }
    
    public int updatePhoneByUserName(String userName, String newPhone) {
        return userDetailsRepository.updatePhoneByUserName(userName, newPhone);
    }
    
    // ============== L1 CACHE ISSUE DEMONSTRATION ==============
    
    @Transactional
    public Map<String, Object> demonstrateCacheIssue(String userName) {
        Map<String, Object> result = new HashMap<>();
        
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║  L1 CACHE ISSUE DEMONSTRATION                  ║");
        System.out.println("╚════════════════════════════════════════════════╝\n");
        
        // Step 1: Load entity (now in L1 cache)
        System.out.println("STEP 1: Loading user by name: " + userName);
        List<UserDetails> users = userDetailsRepository.findByUserName(userName);
        
        if (users.isEmpty()) {
            result.put("error", "User not found: " + userName);
            return result;
        }
        
        UserDetails user = users.get(0);
        Long userId = user.getUserId();
        System.out.println("✓ Found user: " + user.getName() + " (ID: " + userId + ", Phone: " + user.getPhone() + ")");
        System.out.println("✓ Entity now in L1 CACHE (Persistence Context)");
        result.put("step1_found", true);
        result.put("step1_userId", userId);
        result.put("step1_name", user.getName());
        result.put("step1_phone", user.getPhone());
        
        System.out.println("\n" + "─".repeat(50));
        
        // Step 2: Bulk delete (bypasses L1 cache)
        System.out.println("\nSTEP 2: Executing bulk DELETE via JPQL");
        System.out.println("⚠️  This executes DIRECTLY on database");
        System.out.println("⚠️  BYPASSES EntityManager/L1 Cache!");
        int deletedCount = userDetailsRepository.deleteByUserNameCorrect(userName);
        System.out.println("✓ Deleted " + deletedCount + " row(s) from DATABASE");
        System.out.println("✗ L1 Cache NOT updated - entity still in memory!");
        result.put("step2_deletedFromDb", deletedCount);
        
        System.out.println("\n" + "─".repeat(50));
        
        // Step 3: Try to find again (STILL IN L1 CACHE!)
        System.out.println("\nSTEP 3: Trying to find user by ID again: " + userId);
        Optional<UserDetails> stillThereOptional = userDetailsRepository.findById(userId);
        
        if (stillThereOptional.isPresent()) {
            UserDetails stillThere = stillThereOptional.get();
            System.out.println("❌ PROBLEM: Still found in L1 CACHE!");
            System.out.println("   User: " + stillThere.getName() + " (ID: " + stillThere.getUserId() + ")");
            System.out.println("   ⚠️  Database: DELETED ✓");
            System.out.println("   ⚠️  L1 Cache: STILL EXISTS ✗");
            System.out.println("\n   This is STALE DATA from cache!");
            result.put("step3_foundInCache", true);
            result.put("step3_name", stillThere.getName());
            result.put("step3_issue", "Entity deleted from DB but still in L1 cache!");
        } else {
            System.out.println("✓ Not found (L1 cache was cleared somewhere)");
            result.put("step3_foundInCache", false);
        }
        
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║  CONCLUSION                                    ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        System.out.println("Bulk @Query operations bypass L1 cache!");
        System.out.println("Solution: Add @Modifying(clearAutomatically = true)");
        System.out.println("Or manually call entityManager.clear()\n");
        
        result.put("summary", "Bulk delete bypassed L1 cache - entity still in memory!");
        
        return result;
    }
    
    @Transactional
    public Map<String, Object> demonstrateCacheIssueSolved(String userName) {
        Map<String, Object> result = new HashMap<>();
        
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║  L1 CACHE WITH clearAutomatically = true      ║");
        System.out.println("╚════════════════════════════════════════════════╝\n");
        
        System.out.println("STEP 1: Loading user: " + userName);
        List<UserDetails> users = userDetailsRepository.findByUserName(userName);
        
        if (users.isEmpty()) {
            result.put("error", "User not found");
            return result;
        }
        
        UserDetails user = users.get(0);
        Long userId = user.getUserId();
        System.out.println("✓ Found: " + user.getName() + " (ID: " + userId + ")");
        result.put("step1_found", true);
        result.put("step1_userId", userId);
        
        System.out.println("\n" + "─".repeat(50));
        System.out.println("\nSTEP 2: Bulk DELETE with clearAutomatically = true");
        int deletedCount = userDetailsRepository.deleteByUserNameWithClearCache(userName);
        System.out.println("✓ Deleted from DB: " + deletedCount);
        System.out.println("✓ L1 Cache CLEARED automatically!");
        result.put("step2_deleted", deletedCount);
        
        System.out.println("\n" + "─".repeat(50));
        System.out.println("\nSTEP 3: Finding by ID: " + userId);
        Optional<UserDetails> afterDelete = userDetailsRepository.findById(userId);
        
        if (afterDelete.isPresent()) {
            System.out.println("❌ Still found (unexpected)");
            result.put("step3_found", true);
        } else {
            System.out.println("✅ NOT FOUND - cache was cleared!");
            System.out.println("✅ No stale data issue!");
            result.put("step3_found", false);
        }
        
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║  ✅ PROBLEM SOLVED!                           ║");
        System.out.println("╚════════════════════════════════════════════════╝\n");
        
        result.put("summary", "clearAutomatically = true solved the cache issue!");
        return result;
    }
    
    // ============== NAMED QUERIES ==============
    
    public List<UserDetails> findUserByNameNamedQuery(String name) {
        return userDetailsRepository.findByName(name);
    }
    
    public List<UserDetails> findUserByPhoneNamedQuery(String phone) {
        return userDetailsRepository.findByPhone(phone);
    }
}

