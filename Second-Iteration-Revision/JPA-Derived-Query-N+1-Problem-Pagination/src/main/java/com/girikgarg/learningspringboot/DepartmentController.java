package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    
    @Autowired
    private DepartmentService departmentService;
    
    // Create department with employees
    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        // Ensure bidirectional relationship is set
        for (Employee emp : department.getEmployees()) {
            emp.setDepartment(department);
        }
        return departmentService.saveDepartment(department);
    }
    
    // Get all departments
    @GetMapping
    public List<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }
    
    // ============== N+1 PROBLEM DEMONSTRATION ==============
    
    /**
     * BETTER: Demonstrates Batching with @BatchSize
     * 
     * Endpoint: GET /api/departments/demo-batch-size?location=USA
     * 
     * Watch console logs to see:
     * - 1 query to fetch departments
     * - Batched queries using IN clause (e.g., WHERE department_id IN (1,2,3,...,10))
     * 
     * @BatchSize(size=10) reduces N queries to ceiling(N/10) queries
     * 
     * Example with 25 departments:
     * Query 1: SELECT * FROM departments WHERE location LIKE '%USA%'
     * Query 2: SELECT * FROM employees WHERE department_id IN (1,2,3,4,5,6,7,8,9,10)
     * Query 3: SELECT * FROM employees WHERE department_id IN (11,12,13,14,15,16,17,18,19,20)
     * Query 4: SELECT * FROM employees WHERE department_id IN (21,22,23,24,25)
     * Total: 4 queries (vs 26 without batching)
     */
    @GetMapping("/demo-batch-size")
    public List<String> demonstrateBatchSizeOptimization(
            @RequestParam(defaultValue = "USA") String location) {
        return departmentService.demonstrateBatchSizeOptimization(location);
    }
    
    /**
     * BAD: Demonstrates N+1 Problem with One-to-Many
     * 
     * Endpoint: GET /api/departments/demo-n-plus-1?location=USA
     * 
     * Watch console logs to see:
     * - 1 query to fetch departments
     * - N separate queries to fetch employees for each department
     * 
     * Example with 3 departments:
     * Query 1: SELECT * FROM departments WHERE location LIKE '%USA%'
     * Query 2: SELECT * FROM employees WHERE department_id = 1
     * Query 3: SELECT * FROM employees WHERE department_id = 2
     * Query 4: SELECT * FROM employees WHERE department_id = 3
     * Total: 4 queries (1 + 3)
     */
    @GetMapping("/demo-n-plus-1")
    public List<String> demonstrateN1Problem(
            @RequestParam(defaultValue = "USA") String location) {
        return departmentService.demonstrateN1Problem(location);
    }
    
    /**
     * GOOD: Solves N+1 Problem with JOIN FETCH
     * 
     * Endpoint: GET /api/departments/solve-n-plus-1?location=USA
     * 
     * Watch console logs to see:
     * - Only 1 query with LEFT JOIN to fetch departments and employees together
     * 
     * Query 1: SELECT d.*, e.* FROM departments d 
     *          LEFT JOIN employees e ON d.id = e.department_id 
     *          WHERE d.location LIKE '%USA%'
     * Total: 1 query only!
     */
    @GetMapping("/solve-n-plus-1")
    public List<String> solveN1ProblemWithJoinFetch(
            @RequestParam(defaultValue = "USA") String location) {
        return departmentService.solveN1ProblemWithJoinFetch(location);
    }
}

