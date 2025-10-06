package com.girikgarg.learningspringboot;

import jakarta.persistence.*;

@Entity
@Table(name = "user_permission")
public class UserPermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // eg: ORDER_READ, SALES_READ

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
}
