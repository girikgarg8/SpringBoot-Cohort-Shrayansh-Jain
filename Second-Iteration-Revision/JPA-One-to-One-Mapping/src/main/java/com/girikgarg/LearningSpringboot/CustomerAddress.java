package com.girikgarg.learningspringboot;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class CustomerAddress {
    @EmbeddedId
    private CustomerAddressCK id;
    private String city;
    private String state;
    private String country;

    public CustomerAddress() {
    }

    public CustomerAddress(CustomerAddressCK id, String city, String state, String country) {
        this.id = id;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public CustomerAddressCK getId() {
        return id;
    }

    public void setId(CustomerAddressCK id) {
        this.id = id;
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
}
