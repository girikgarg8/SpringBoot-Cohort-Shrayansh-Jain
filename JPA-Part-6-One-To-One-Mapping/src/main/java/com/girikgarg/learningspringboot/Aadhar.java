package com.girikgarg.learningspringboot;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Aadhar {
//    @EmbeddedId
//    @JsonUnwrapped
//    private AadharCompositeKey aadharCompositeKey;
//
//    public Aadhar() { }
//
//    public Aadhar(AadharCompositeKey aadharCompositeKey) {
//        this.aadharCompositeKey = aadharCompositeKey;
//    }
//
//    public AadharCompositeKey getAadharCompositeKey() {
//        return aadharCompositeKey;
//    }
//
//    public void setAadharCompositeKey(AadharCompositeKey aadharCompositeKey) {
//        this.aadharCompositeKey = aadharCompositeKey;
//    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String phone;

    @OneToOne(mappedBy = "aadhar", fetch = FetchType.EAGER)
//    @JsonBackReference
    private User user;

    public Aadhar() { }

    public Aadhar(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
