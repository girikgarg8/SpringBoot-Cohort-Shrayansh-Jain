package com.girikgarg.learningspringboot;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

//    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Employee> employees;

    public Department() {}

    public Department(String name) {
        this.name = name;
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;

        for (Employee employee : employees) {
            employee.setDepartment(this);
        }

        /**
         * It syncs the bidirectional association. In JPA, the owning side is Employee.department (ManyToOne). Updating only Department.employees does not update the FK.
         * Setting employee.setDepartment(this) ensures the FK dept_id_fk is set, so Hibernate persists/updates rows correctly.
         */
    }

    public DepartmentDTO toDTO() {
        return new DepartmentDTO(this);
    }
}
