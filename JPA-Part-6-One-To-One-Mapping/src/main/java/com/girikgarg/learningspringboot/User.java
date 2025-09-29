package com.girikgarg.learningspringboot;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // USER is reserved keyword
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name="user_aadhar_id", referencedColumnName = "id")
    @JoinColumns({
            @JoinColumn(name="aadhaar_phone", referencedColumnName = "phone"),
            @JoinColumn(name="aadhar_email", referencedColumnName = "email")
    })
    private Aadhar aadhar;

    public User() { }

    public User(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
