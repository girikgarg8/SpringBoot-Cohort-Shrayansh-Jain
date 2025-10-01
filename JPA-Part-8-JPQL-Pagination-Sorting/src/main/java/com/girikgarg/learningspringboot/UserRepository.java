package com.girikgarg.learningspringboot;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findByName(String username, Pageable pageable);

    @Transactional
    void deleteByName(String username);

    User findByIdOrName(Long id, String username);

    @Query("SELECT u from User u WHERE u.name = :userFirstName")
    List<User> findByUsername(@Param("userFirstName") String username);

//    @Query("SELECT u FROM User u JOIN u.aadhar ad WHERE u.name= :userFirstName")
    @Query("SELECT new com.girikgarg.learningspringboot.UserDTO(u.name, ad.phone) FROM User u JOIN u.aadhar ad WHERE u.name = :userFirstName")
    List<UserDTO> findUserDetailsWithAadhar(@Param("userFirstName") String username);

//    @Query("select u from User u JOIN FETCH u.addresses ad where u.name = :userFirstName")
    @Query("select u from User u JOIN u.addresses ad where u.name = :userFirstName")
    List<User> findUserWithAddressByName(@Param("userFirstName") String name);

    @Query("DELETE FROM User u WHERE u.name = :userFirstName")
    @Transactional
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    void deleteUser(@Param("userFirstName") String name);
}
