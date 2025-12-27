package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void createTable() {
        jdbcTemplate.execute("CREATE TABLE APP_USERS(USER_ID INT AUTO_INCREMENT PRIMARY KEY, USER_NAME VARCHAR(100), AGE INT)");
    }

    public void insertUser(String name, int age) {
        String insertQuery = "INSERT INTO APP_USERS (USER_NAME, AGE) VALUES(?,?)";
        jdbcTemplate.update(insertQuery, name, age);
    }

    public List <User> getUsers() {
        String selectQuery = "SELECT * FROM APP_USERS";
        return jdbcTemplate.query(selectQuery, (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getInt("USER_ID"));
            user.setUserName(rs.getString("USER_NAME"));
            user.setAge(rs.getInt("AGE"));
            return user;
        }); 
    }

}
