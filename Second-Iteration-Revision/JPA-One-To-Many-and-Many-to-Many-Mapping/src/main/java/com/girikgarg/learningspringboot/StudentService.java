package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private UniversityRepository universityRepository;
    
    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }
    
    @Transactional
    public Student enrollStudentInUniversity(String studentName, String studentEmail, Long universityId) {
        // Create a new student
        Student student = new Student(studentName, studentEmail);
        
        // Find the university
        University university = universityRepository.findById(universityId).orElse(null);
        
        if (university != null) {
            // Set the Many-to-One relationship
            student.setUniversity(university);
        }
        
        return studentRepository.save(student);
    }
    
    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
}



