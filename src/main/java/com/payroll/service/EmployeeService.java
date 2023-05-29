package com.payroll.service;

import com.payroll.model.Employee;
import com.payroll.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + id));
    }

    public Employee updateEmployee(Employee updatedEmployee) {
        return employeeRepository.findById(updatedEmployee.getId())
                .map(employee -> {
                    employee.setName(updatedEmployee.getName());
                    employee.setRole(updatedEmployee.getRole());
                    return employeeRepository.save(employee);
                })
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with id: " + updatedEmployee.getId()));
    }

    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
}
