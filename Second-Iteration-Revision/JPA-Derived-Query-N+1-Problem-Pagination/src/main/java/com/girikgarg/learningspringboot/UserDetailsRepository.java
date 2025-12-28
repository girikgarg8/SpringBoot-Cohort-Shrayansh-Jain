package com.girikgarg.learningspringboot;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
    
    // Simple derived query: WHERE user_name = ?
    List<UserDetails> findUserDetailsByName(String userName);
    
    // AND condition: WHERE user_name = ? AND phone = ?
    List<UserDetails> findUserDetailsByNameAndPhone(String userName, String phone);
    
    // AND & OR condition: WHERE user_name = ? AND phone = ? OR user_id = ?
    List<UserDetails> findUserDetailsByNameAndPhoneOrUserId(String userName, String phone, Long id);
    
    // IN clause: WHERE user_name IN (?)
    List<UserDetails> findUserDetailsByNameIsIn(List<String> userNames);
    
    // LIKE clause: WHERE user_name LIKE ?
    List<UserDetails> findUserDetailsByNameLike(String userName);
    
    // Comparison operators
    List<UserDetails> findUserDetailsByUserIdBetween(Long startId, Long endId);
    
    List<UserDetails> findUserDetailsByUserIdLessThan(Long userId);
    
    List<UserDetails> findUserDetailsByUserIdGreaterThan(Long userId);
    
    List<UserDetails> findUserDetailsByPhoneIsNull();
    
    List<UserDetails> findUserDetailsByPhoneIsNotNull();
    
    // StartingWith, EndingWith, Containing
    List<UserDetails> findUserDetailsByNameStartingWith(String prefix);
    
    List<UserDetails> findUserDetailsByNameEndingWith(String suffix);
    
    List<UserDetails> findUserDetailsByNameContaining(String substring);
    
    // Delete operations (requires @Transactional)
    void deleteByName(String userName);
    
    Long deleteByUserId(Long userId);
    
    // ============== PAGINATION AND SORTING VARIATIONS ==============
    
    // 1. Pagination only - with Pageable
    Page<UserDetails> findUserDetailsByNameStartingWith(String userName, Pageable page);
    
    // 2. Sorting only - with Sort
    List<UserDetails> findUserDetailsByNameStartingWith(String userName, Sort sort);
    
    // 3. Different query method with Pageable
    Page<UserDetails> findUserDetailsByNameContaining(String userName, Pageable page);
    
    // 4. Different query method with Sort
    List<UserDetails> findUserDetailsByNameContaining(String userName, Sort sort);
    
    // 5. Simple findBy with Pageable
    Page<UserDetails> findUserDetailsByName(String userName, Pageable page);
    
    // 6. Simple findBy with Sort
    List<UserDetails> findUserDetailsByName(String userName, Sort sort);
    
    // 7. FindByNameEndingWith with Pageable
    Page<UserDetails> findUserDetailsByNameEndingWith(String suffix, Pageable page);
    
    // 8. FindByNameEndingWith with Sort
    List<UserDetails> findUserDetailsByNameEndingWith(String suffix, Sort sort);
    
    // 9. Complex query with Pageable (AND condition)
    Page<UserDetails> findUserDetailsByNameAndPhone(String userName, String phone, Pageable page);
    
    // 10. Complex query with Sort (AND condition)
    List<UserDetails> findUserDetailsByNameAndPhone(String userName, String phone, Sort sort);
    
    // 11. Between query with Pageable
    Page<UserDetails> findUserDetailsByUserIdBetween(Long startId, Long endId, Pageable page);
    
    // 12. Between query with Sort
    List<UserDetails> findUserDetailsByUserIdBetween(Long startId, Long endId, Sort sort);
    
    // 13. GreaterThan with Pageable
    Page<UserDetails> findUserDetailsByUserIdGreaterThan(Long userId, Pageable page);
    
    // 14. GreaterThan with Sort
    List<UserDetails> findUserDetailsByUserIdGreaterThan(Long userId, Sort sort);
    
    // 15. IsNotNull with Pageable
    Page<UserDetails> findUserDetailsByPhoneIsNotNull(Pageable page);
    
    // 16. IsNotNull with Sort
    List<UserDetails> findUserDetailsByPhoneIsNotNull(Sort sort);

    // ============== JPQL (Java Persistence Query Language) QUERIES ==============
    
    /**
     * JPQL Query Syntax Explanation:
     * 
     * @Query("SELECT u FROM UserDetails u WHERE u.name = :userFirstName")
     *        |      |      |            |           |            |
     *        |      |      |            |           |            └─ Named parameter (bound with @Param)
     *        |      |      |            |           └─ Entity FIELD name (not column name)
     *        |      |      |            └─ Entity alias (can be any name)
     *        |      |      └─ ENTITY name (not table name)
     *        |      └─ Entity alias - returns all fields
     *        └─ SELECT keyword
     * 
     * Key Points:
     * 1. "u" is an ENTITY ALIAS - it returns all fields from UserDetails entity
     * 2. "UserDetails" is the ENTITY name, NOT the table name (@Table annotation may differ)
     * 3. "u.name" is the ENTITY FIELD name, NOT the database column name
     * 4. ":userFirstName" is a NAMED PARAMETER that binds with @Param("userFirstName")
     * 5. Return type can be List<UserDetails> or single UserDetails
     *    - If query returns multiple rows but return type is single object, JPQL throws exception
     * 
     * JPQL vs Native SQL:
     * - JPQL works with ENTITIES and FIELDS (Java objects)
     * - Native SQL works with TABLES and COLUMNS (database)
     */
    @Query("SELECT u FROM UserDetails u WHERE u.name = :userFirstName")
    List<UserDetails> findByUserName(@Param("userFirstName") String userName);
    
    // ============== JPQL QUERIES WITH JOIN ==============
    
    /**
     * JPQL Query with JOIN - Returns entire entities
     * 
     * Note: We don't specifically need to put "ON" clause here
     * JPA will automatically generate the ON condition based on the @JoinColumn mapping
     * 
     * Hibernate generates:
     * SELECT ud1_0.user_id, ud1_0.user_name, ud1_0.phone, ud1_0.user_address
     * FROM user_details ud1_0
     * JOIN user_address ua1_0 ON ua1_0.id=ud1_0.user_address
     * WHERE ud1_0.user_name=?
     */
    @Query("SELECT ud FROM UserDetails ud JOIN ud.userAddress ad WHERE ud.name = :userFirstName")
    List<UserDetails> findUserDetailsWithAddress(@Param("userFirstName") String userName);
    
    /**
     * JPQL Query with JOIN - Returns specific fields as Object[]
     * 
     * When selecting specific fields, return type MUST be List<Object[]>
     * Object[0] = ud.name (String)
     * Object[1] = ad.country (String)
     */
    @Query("SELECT ud.name, ad.country FROM UserDetails ud JOIN ud.userAddress ad WHERE ud.name = :userFirstName")
    List<Object[]> findUserDetailsWithAddressAsObjectArray(@Param("userFirstName") String userName);
    
    /**
     * JPQL Query with Constructor Expression - Returns DTO directly
     * 
     * Instead of Object[], we can use constructor expression with "new" keyword
     * This directly creates UserDTO objects with selected fields
     * 
     * Note: Must provide FULLY QUALIFIED CLASS NAME for the DTO
     * Format: new com.girikgarg.learningspringboot.UserDTO(field1, field2)
     */
    @Query("SELECT new com.girikgarg.learningspringboot.UserDTO(ud.name, ad.country) FROM UserDetails ud JOIN ud.userAddress ad WHERE ud.name = :userFirstName")
    List<UserDTO> findUserDetailsWithAddressAsDTO(@Param("userFirstName") String userName);
    
    // ============== WRITE OPERATIONS WITH @Query ==============
    
    // ❌ WITHOUT @Modifying - throws error
    @Query("DELETE FROM UserDetails ud WHERE ud.name = :userFirstName")
    void deleteByUserNameIncorrect(@Param("userFirstName") String userName);
    
    // ✅ WITH @Modifying but WITHOUT clearAutomatically - causes L1 cache issues
    @Modifying
    @Transactional
    @Query("DELETE FROM UserDetails ud WHERE ud.name = :userFirstName")
    int deleteByUserNameCorrect(@Param("userFirstName") String userName);
    
    // ✅ WITH @Modifying(clearAutomatically = true) - clears L1 cache automatically
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("DELETE FROM UserDetails ud WHERE ud.name = :userFirstName")
    int deleteByUserNameWithClearCache(@Param("userFirstName") String userName);
    
    // UPDATE example with clearAutomatically
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("UPDATE UserDetails ud SET ud.phone = :newPhone WHERE ud.name = :userName")
    int updatePhoneByUserName(@Param("userName") String userName, @Param("newPhone") String newPhone);
    
    // ============== NAMED QUERIES ==============
    
    List<UserDetails> findByName(@Param("name") String name);
    
    List<UserDetails> findByPhone(@Param("phone") String phone);
}

