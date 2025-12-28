package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    
    @Autowired
    private StudentService studentService;
    
    // Create student with university
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.saveStudent(student);
    }
    
    // Enroll a student in a university (demonstrates Many-to-One)
    @PostMapping("/enroll")
    public Student enrollStudent(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam Long universityId) {
        return studentService.enrollStudentInUniversity(name, email, universityId);
    }
    
    @GetMapping("/{id}")
    public Student getStudent(@PathVariable Long id) {
        // This will return the student with their university (Many-to-One relationship)
        return studentService.getStudent(id);
    }
    
    @GetMapping
    public List<Student> getAllStudents() {
        // This will return all students with their universities
        return studentService.getAllStudents();
    }
}

