package com.girikgarg.learningspringboot;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class Customer {
    @EmbeddedId
    private CustomerDetailsCK customerDetailsCK;
    private String phone;

    public Customer() {

    }

    // getters and setters
}
