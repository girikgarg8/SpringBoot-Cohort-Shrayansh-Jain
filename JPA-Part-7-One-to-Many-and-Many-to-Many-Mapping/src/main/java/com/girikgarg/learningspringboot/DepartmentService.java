package com.girikgarg.learningspringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    public DepartmentDTO getDepartment(Long id) {
        return departmentRepository.findById(id).get().toDTO();
    }

    public Department findById(Long id) {
        return departmentRepository.findById(id).get();
    }

    public void save(Department department) {
        departmentRepository.save(department);
    }
}


