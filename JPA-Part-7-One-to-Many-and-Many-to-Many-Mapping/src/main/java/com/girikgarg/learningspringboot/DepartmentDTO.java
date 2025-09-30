package com.girikgarg.learningspringboot;

import java.util.List;

public class DepartmentDTO {
    private String departmentName;
    private Long departmentId;
    private List<Employee> employees;

    public DepartmentDTO(Department department) {
        this.departmentName = department.getName();
        this.departmentId = department.getId();

        System.out.println("Going to query the employees now");
        this.employees = department.getEmployees();
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
