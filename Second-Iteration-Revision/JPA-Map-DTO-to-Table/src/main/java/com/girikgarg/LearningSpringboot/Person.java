package com.girikgarg.learningspringboot;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(PersonDetailsCK.class)
public class Person {
    @Id
    private String name;
    @Id
    private String address;
    private String phone;

    public Person() {

    }

    // getters and setters
}
