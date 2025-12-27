package com.girikgarg.learningspringboot;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.UniqueConstraint;

@Table(name = "MY_USER_DETAILS", schema = "ONBOARDING", uniqueConstraints = {
    @UniqueConstraint(columnNames = "phone"), // single column unique constraint
    @UniqueConstraint(columnNames = {"full_name", "email"}) // composite unique constraint
},
indexes = {
    @Index(name = "index_phone", columnList = "phone"), // index on single column
    @Index(name = "index_name_email", columnList = "full_name,email") // index on composite column
})
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "userDetailsCache")
public class UserDetails {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "full_name", unique = true, nullable = false, length = 255)
    private String name;
    private String email;
    private String phone;

    public UserDetails() {
    }
    
    public UserDetails(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    
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
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

