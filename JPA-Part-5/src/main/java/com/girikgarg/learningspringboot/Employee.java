package com.girikgarg.learningspringboot;

import jakarta.persistence.*;

@Entity
public class Employee {
    // CREATE SEQUENCE db_user_seq INCREMENT BY 25 START WITH 100 MAXVALUE 9999;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unique_user_seq")
    @SequenceGenerator(name = "unique_user_seq", sequenceName = "db_user_seq", initialValue = 100, allocationSize = 5)
    private Long id;
    private String name;
    private String phone;

    public Employee() {

    }

    public Employee(Long id, String name, String phone) {
        this.id = id;
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
