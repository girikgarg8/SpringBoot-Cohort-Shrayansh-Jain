package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
     *
     * curl --location 'http://localhost:8080/api/departments' \
     * --header 'Content-Type: application/json' \
     * --data '{
     *   "name": "Engineering",
     *   "employees": [
     *     {"name": "Alice"},
     *     {"name": "Bob"}
     *   ]
     * }'
     *
     */

    @PostMapping
    public Department create(@RequestBody Department department) {
        return departmentService.createDepartment(department);
    }

    //    curl --location 'http://localhost:8080/api/departments/1'
    @GetMapping("/{id}")
    public DepartmentDTO findById (@PathVariable Long id) {
        return departmentService.getDepartment(id);
    }

    // curl --location --request DELETE 'http://localhost:8080/api/departments/1'
    @DeleteMapping("/{id}")
    public Department deleteById(@PathVariable Long id) {
        Department department = departmentService.findById(id);
        department.getEmployees().remove(0);
        departmentService.save(department);
        return department;
    }

}


