package com.girikgarg.learningspringboot;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
//@Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region="userDetailsCache")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "userDetailsCache")

@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="userDetailsCache")
//@Table(name="USER_DETAILS", schema="ONBOARDING", uniqueConstraints = {
//        @UniqueConstraint(columnNames = "id"), // single column unique constraint
//        @UniqueConstraint(columnNames = {"user_name", "email"}) // composite unique constraint
//},
//indexes = {
//        @Index(name="index_id", columnList = "id"), // index on single column
//        @Index(name="index_name_email", columnList = "user_name,email") // index on composite column
//})

//@IdClass(UserDetailsCompositeKey.class)
public class UserDetails {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    @EmbeddedId
    private UserDetailsCompositeKeyEmbeddable userDetailsCompositeKey;

//    @Column(name="user_name", unique=true, nullable = false, length=255)
//
//    @Id
//    private String name;
//    @Id
//    private String email;

    public UserDetails() { }

//    public UserDetails(String name, String email) {
//        this.name = name;
//        this.email = email;
//    }

    // Getters and setters
//    public Long getId() {
//        return id;
//    }
//    public void setId(Long id) {
//        this.id = id;
//    }

//    public String getName() {
//        return name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//    public void setEmail(String email) {
//        this.email = email;
//    }
}