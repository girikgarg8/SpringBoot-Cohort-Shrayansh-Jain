package com.girikgarg.learningspringboot;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

/**
 * Department entity demonstrating One-to-Many relationship
 * One Department has Many Employees
 */
@Entity
@Table(name = "departments")
public class Department {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String location;
    
    /**
     * One-to-Many relationship with Employee
     * FetchType.LAZY (default for collections) - Employees are loaded only when accessed
     * This is where N+1 problem occurs if not handled properly!
     * 
     * @BatchSize(size=10) - Batches lazy loading into groups of 10
     * Instead of N queries, it makes ceiling(N/10) queries
     * Example: 25 departments = 3 batched queries instead of 25 individual queries
     */
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    @JsonManagedReference
    private List<Employee> employees = new ArrayList<>();
    
    public Department() {
    }
    
    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }
    
    // Helper method to add employee
    public void addEmployee(Employee employee) {
        employees.add(employee);
        employee.setDepartment(this);
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
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public List<Employee> getEmployees() {
        return employees;
    }
    
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}

