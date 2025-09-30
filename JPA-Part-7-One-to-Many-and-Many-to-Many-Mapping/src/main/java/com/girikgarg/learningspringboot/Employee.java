package com.girikgarg.learningspringboot;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name="dept_id_fk", referencedColumnName = "id")
    private Department department;

    public Employee() {}

    public Employee(String name) {
        this.name = name;
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public void setId(Long id) { this.id = id; }

    public Department getDepartment() { return department; }

    public void setDepartment(Department department) { this.department = department; }
}
