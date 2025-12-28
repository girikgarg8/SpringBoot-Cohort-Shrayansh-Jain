package com.girikgarg.learningspringboot;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    
    /**
     * BAD: Query without JOIN FETCH - Causes N+1 Problem
     * 
     * This fetches only departments (1 query)
     * When we access dept.getEmployees() for each department, 
     * Hibernate executes N separate queries to fetch employees
     * 
     * Example: If 3 departments exist:
     * - 1 query to fetch departments
     * - 3 queries to fetch employees for each department
     * Total = 4 queries (1 + N)
     */
    List<Department> findByLocationContaining(String location);
    
    /**
     * GOOD: Query WITH JOIN FETCH - Solves N+1 Problem
     * 
     * JOIN FETCH loads departments and their employees in ONE query
     * Only 1 query is executed regardless of how many departments are returned
     * 
     * Note: LEFT JOIN FETCH ensures departments without employees are also included
     */
    @Query("SELECT d FROM Department d LEFT JOIN FETCH d.employees WHERE d.location LIKE %:location%")
    List<Department> findByLocationWithEmployees(String location);
    
    /**
     * Alternative: Using @EntityGraph annotation to solve N+1
     * @EntityGraph is another way to eagerly fetch associations
     */
    // @EntityGraph(attributePaths = {"employees"})
    // List<Department> findByLocationContaining(String location);
}

