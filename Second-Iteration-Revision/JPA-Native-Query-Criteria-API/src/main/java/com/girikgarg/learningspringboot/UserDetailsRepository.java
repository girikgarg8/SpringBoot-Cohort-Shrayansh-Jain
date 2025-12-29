package com.girikgarg.learningspringboot;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {
    
    // Native Query returning all fields - works fine, JPA maps automatically
    @Query(value = "SELECT * FROM user_details WHERE user_name = :userFirstName", nativeQuery = true)
    List<UserDetails> getUserDetailsByNameNativeQuery(@Param("userFirstName") String userName);
    
    // Native Query returning partial fields - THROWS ERROR without proper mapping
    // This will fail: Column "user_id" not found [42122-224]
    @Query(value = "SELECT user_name, phone FROM user_details WHERE user_name = :userFirstName", nativeQuery = true)
    List<UserDetails> getUserDetailsByNameNativeQueryPartial(@Param("userFirstName") String userName);
    
    // Solution 1: Using @NamedNativeQuery with @SqlResultSetMapping
    // Defined in UserDetails entity with proper column mapping
    List<UserDTO> getUserDetailsByName(@Param("userFirstName") String userName);
    
    // Solution 2: Manual mapping - return Object[] and map in service
    @Query(value = "SELECT user_name, phone FROM user_details WHERE user_name = :userFirstName", nativeQuery = true)
    List<Object[]> getUserDetailsByNameNativeQueryManual(@Param("userFirstName") String userName);
    
    // Way 2: Using Spring Data JPA Pageable with Native Query
    @Query(value = "SELECT * FROM user_details ud WHERE ud.user_name = :userName", nativeQuery = true)
    List<UserDetails> getUserDetailsByNameNativeQuery(@Param("userName") String userName, Pageable pageable);
}

