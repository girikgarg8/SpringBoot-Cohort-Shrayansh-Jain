package com.girikgarg.learningspringboot;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeService {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Employee createHardcodedEmployee() {
        Employee employee = new Employee(null, "John Doe", "+1-555-123-4567");
        entityManager.persist(employee);
        entityManager.flush();
        return employee;
    }
}


