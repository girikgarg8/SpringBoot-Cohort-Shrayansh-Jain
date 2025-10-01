package com.girikgarg.learningspringboot;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    @Query(name= "UserDetails.getUserDetailsByName", nativeQuery = true)
    public UserDTO findByUsernameWithNativeQuery(@Param("userFirstName") String username);

    @Query(value = "SELECT user_name FROM user_details WHERE user_name = :userFirstName", nativeQuery = true)
    List<Object[]> getUserDetailsByNameWithNativeQuery(@Param("userFirstName") String userName, Pageable pageable);
}
