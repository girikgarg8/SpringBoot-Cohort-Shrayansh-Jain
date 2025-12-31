package com.girikgarg.learningspringboot;

/**
 * DTO (Data Transfer Object) for custom query results
 * Used when we want to return specific fields instead of entire entities
 */
public class UserDTO {
    
    private String userName;
    private String country;
    
    // Default constructor
    public UserDTO() {
    }
    
    // Constructor to populate from UserDetails entity
    public UserDTO(String userName, String country) {
        this.userName = userName;
        this.country = country;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
}


