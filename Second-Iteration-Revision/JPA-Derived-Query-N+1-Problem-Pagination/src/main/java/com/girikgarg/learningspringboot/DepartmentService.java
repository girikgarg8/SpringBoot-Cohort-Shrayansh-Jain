package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {
    
    @Autowired
    private DepartmentRepository departmentRepository;
    
    // Save department with employees
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }
    
    // Get all departments
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    
    // Get department by id
    public Department getDepartment(Long id) {
        return departmentRepository.findById(id).orElse(null);
    }
    
    // ============== N+1 PROBLEM DEMONSTRATION ==============
    
    /**
     * BETTER: Demonstrates Batching with @BatchSize
     * 
     * Instead of N+1 queries, @BatchSize groups the queries into batches
     * 
     * Without @BatchSize (N+1):
     * - Query 1: SELECT * FROM departments
     * - Query 2: SELECT * FROM employees WHERE department_id = 1
     * - Query 3: SELECT * FROM employees WHERE department_id = 2
     * - Query 4: SELECT * FROM employees WHERE department_id = 3
     * Total: 1 + N queries
     * 
     * With @BatchSize(size=10):
     * - Query 1: SELECT * FROM departments
     * - Query 2: SELECT * FROM employees WHERE department_id IN (1, 2, 3, ..., 10)
     * - Query 3: SELECT * FROM employees WHERE department_id IN (11, 12, 13, ..., 20)
     * Total: 1 + ceiling(N/10) queries
     * 
     * For 25 departments: 1 + 3 queries = 4 queries (vs 26 without batching)
     */
    @Transactional
    public List<String> demonstrateBatchSizeOptimization(String location) {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║   ⚡ BATCHING WITH @BatchSize (One-to-Many)     ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println("\n📍 Location filter: " + location);
        System.out.println("\n🔍 Step 1: Fetching departments...");
        
        // 1 query to fetch all departments
        List<Department> departments = departmentRepository.findByLocationContaining(location);
        System.out.println("✅ Found " + departments.size() + " departments");
        System.out.println("   SQL Query 1: SELECT * FROM departments WHERE location LIKE '%" + location + "%'");
        
        List<String> results = new ArrayList<>();
        
        System.out.println("\n🔍 Step 2: Accessing employees for each department...");
        System.out.println("   ⚡ @BatchSize(size=10) will batch these queries!");
        System.out.println();
        
        int batchCount = (int) Math.ceil(departments.size() / 10.0);
        
        // Instead of N queries, Hibernate batches them
        for (Department dept : departments) {
            String deptName = dept.getName();
            int employeeCount = dept.getEmployees().size();
            String result = deptName + " (" + dept.getLocation() + ") - " + employeeCount + " employees";
            results.add(result);
            System.out.println("   📊 " + result);
        }
        
        System.out.println("\n   SQL Query 2: SELECT * FROM employees WHERE department_id IN (?, ?, ?, ...)");
        if (batchCount > 1) {
            System.out.println("   SQL Query 3: SELECT * FROM employees WHERE department_id IN (?, ?, ?, ...)");
        }
        
        System.out.println("\n📈 TOTAL QUERIES EXECUTED: " + (1 + batchCount));
        System.out.println("   Formula: 1 (departments) + " + batchCount + " (batched employees) = " + (1 + batchCount));
        System.out.println("\n⚡ Batching reduces queries significantly!");
        System.out.println("   Without batching: " + (1 + departments.size()) + " queries");
        System.out.println("   With @BatchSize(10): " + (1 + batchCount) + " queries");
        System.out.println("   Reduction: " + (departments.size() - batchCount) + " fewer queries!");
        System.out.println("╚════════════════════════════════════════════════════╝\n");
        
        return results;
    }
    
    /**
     * BAD: Demonstrates N+1 Problem with One-to-Many relationship
     * 
     * Scenario: Fetch all departments and count their employees
     * 
     * Without JOIN FETCH:
     * - Query 1: SELECT * FROM departments WHERE location LIKE '%location%' (1 query)
     * - Query 2: SELECT * FROM employees WHERE department_id = ? (for dept 1)
     * - Query 3: SELECT * FROM employees WHERE department_id = ? (for dept 2)
     * - Query 4: SELECT * FROM employees WHERE department_id = ? (for dept 3)
     * Total: 1 + N queries
     * 
     * This is the classic N+1 problem!
     */
    @Transactional
    public List<String> demonstrateN1Problem(String location) {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║   ❌ DEMONSTRATING N+1 PROBLEM (One-to-Many)    ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println("\n📍 Location filter: " + location);
        System.out.println("\n🔍 Step 1: Fetching departments...");
        
        // 1 query to fetch all departments
        List<Department> departments = departmentRepository.findByLocationContaining(location);
        System.out.println("✅ Found " + departments.size() + " departments");
        System.out.println("   SQL Query 1: SELECT * FROM departments WHERE location LIKE '%" + location + "%'");
        
        List<String> results = new ArrayList<>();
        
        System.out.println("\n🔍 Step 2: Accessing employees for each department...");
        System.out.println("   ⚠️  WARNING: This will trigger N separate queries!");
        System.out.println();
        
        int queryCount = 1; // Starting from 1 for the initial department query
        
        // N queries - one for EACH department's employees
        for (Department dept : departments) {
            queryCount++;
            String deptName = dept.getName();
            // This line triggers a separate query for each department!
            int employeeCount = dept.getEmployees().size();
            String result = deptName + " (" + dept.getLocation() + ") - " + employeeCount + " employees";
            results.add(result);
            System.out.println("   SQL Query " + queryCount + ": SELECT * FROM employees WHERE department_id = " + dept.getId());
            System.out.println("   📊 " + result);
        }
        
        System.out.println("\n📈 TOTAL QUERIES EXECUTED: " + queryCount);
        System.out.println("   Formula: 1 (departments) + " + departments.size() + " (employees) = " + queryCount);
        System.out.println("\n❌ This is the N+1 Problem!");
        System.out.println("   Performance degrades as number of departments increases!");
        System.out.println("   100 departments = 101 queries!");
        System.out.println("╚════════════════════════════════════════════════════╝\n");
        
        return results;
    }
    
    /**
     * GOOD: Solves N+1 Problem with JOIN FETCH
     * 
     * Uses JOIN FETCH to load departments and their employees in ONE query
     * 
     * With JOIN FETCH:
     * - Query 1: SELECT d.*, e.* FROM departments d 
     *            LEFT JOIN employees e ON d.id = e.department_id 
     *            WHERE d.location LIKE '%location%'
     * Total: 1 query only!
     * 
     * Performance is consistent regardless of number of departments!
     */
    @Transactional
    public List<String> solveN1ProblemWithJoinFetch(String location) {
        System.out.println("\n╔════════════════════════════════════════════════════╗");
        System.out.println("║   ✅ SOLVING N+1 WITH JOIN FETCH (One-to-Many)  ║");
        System.out.println("╚════════════════════════════════════════════════════╝");
        System.out.println("\n📍 Location filter: " + location);
        System.out.println("\n🔍 Fetching departments WITH employees using JOIN FETCH...");
        
        // Only 1 query with JOIN to fetch both departments and employees
        List<Department> departments = departmentRepository.findByLocationWithEmployees(location);
        
        System.out.println("✅ Found " + departments.size() + " departments");
        System.out.println("\n   SQL Query 1 (ONLY ONE!):");
        System.out.println("   SELECT d.*, e.*");
        System.out.println("   FROM departments d");
        System.out.println("   LEFT JOIN employees e ON d.id = e.department_id");
        System.out.println("   WHERE d.location LIKE '%" + location + "%'");
        
        List<String> results = new ArrayList<>();
        
        System.out.println("\n🔍 Accessing employees for each department...");
        System.out.println("   ✅ No additional queries - employees already loaded!");
        System.out.println();
        
        // No additional queries - employees already loaded!
        for (Department dept : departments) {
            String deptName = dept.getName();
            int employeeCount = dept.getEmployees().size();
            String result = deptName + " (" + dept.getLocation() + ") - " + employeeCount + " employees";
            results.add(result);
            System.out.println("   📊 " + result);
        }
        
        System.out.println("\n📈 TOTAL QUERIES EXECUTED: 1");
        System.out.println("   Formula: Just 1 query with JOIN FETCH!");
        System.out.println("\n✅ N+1 Problem SOLVED!");
        System.out.println("   Performance is CONSTANT regardless of department count!");
        System.out.println("   100 departments = still just 1 query!");
        System.out.println("╚════════════════════════════════════════════════════╝\n");
        
        return results;
    }
}

