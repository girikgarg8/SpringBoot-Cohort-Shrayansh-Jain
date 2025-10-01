package com.girikgarg.learningspringboot;

import jakarta.persistence.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NamedQuery(name="findByUserName", query = "SELECT u FROM User u WHERE u.name = :userFirstName")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "aadhar_id")
    private Aadhar aadhar;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @BatchSize(size = 5)
    @JoinColumn(name="user_id")
    private List<Address> addresses = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Aadhar getAadhar() {
        return aadhar;
    }

    public void setAadhar(Aadhar aadhar) {
        this.aadhar = aadhar;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
    
}
