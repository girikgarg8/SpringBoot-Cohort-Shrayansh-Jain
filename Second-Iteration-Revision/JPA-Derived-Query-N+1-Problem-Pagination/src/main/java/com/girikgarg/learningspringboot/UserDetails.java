package com.girikgarg.learningspringboot;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_details")
@NamedQuery(
    name = "findByUserName",
    query = "SELECT u FROM UserDetails u WHERE u.name = :userFirstName"
)
public class UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "user_name")
    private String name;
    
    private String phone;
    
    // One-to-One relationship with UserAddress
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_address", referencedColumnName = "id")
    private UserAddress userAddress;
    
    public UserDetails() {
    }
    
    public UserDetails(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public UserAddress getUserAddress() {
        return userAddress;
    }
    
    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }
}

