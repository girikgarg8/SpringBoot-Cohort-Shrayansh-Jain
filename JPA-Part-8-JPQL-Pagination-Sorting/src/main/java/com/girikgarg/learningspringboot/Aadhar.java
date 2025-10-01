package com.girikgarg.learningspringboot;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Aadhar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;

    public Aadhar() {

    }

    public Aadhar(Long id, String phone) {
        this.id = id;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }
}
