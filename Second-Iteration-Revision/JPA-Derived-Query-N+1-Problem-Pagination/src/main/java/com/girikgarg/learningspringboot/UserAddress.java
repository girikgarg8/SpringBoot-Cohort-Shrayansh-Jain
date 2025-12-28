package com.girikgarg.learningspringboot;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_address")
public class UserAddress {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String street;
    private String city;
    private String state;
    private String country;
    private String pinCode;
    
    public UserAddress() {
    }
    
    public UserAddress(String street, String city, String state, String country, String pinCode) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pinCode = pinCode;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getStreet() {
        return street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getCity() {
        return city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public String getPinCode() {
        return pinCode;
    }
    
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }
}

