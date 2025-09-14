package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import org.modelmapper.ModelMapper;

import com.example.demo.Entity.Employee;
import com.example.demo.repository.EmployeeRepo;
import com.example.demo.response.EmployeeResponse;
import com.example.demo.response.DepartmentResponse;
import com.example.demo.service.DepartmentClient;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private DepartmentClient departmentClient; // REST client to call department-service

    // Get employee by ID with department info
    public EmployeeResponse getEmployeeById(int id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        EmployeeResponse empResp = mapper.map(employee, EmployeeResponse.class);

        if (employee.getDepartmentId() != null) {
            DepartmentResponse deptResp = departmentClient.getDepartmentById(employee.getDepartmentId());
            empResp.setDepartment(deptResp);
        }

        return empResp;
    }

    // Get all employees with their departments
    public List<EmployeeResponse> getAllEmployees() {
        List<Employee> employees = employeeRepo.findAll();

        return employees.stream().map(emp -> {
            EmployeeResponse resp = mapper.map(emp, EmployeeResponse.class);
            if (emp.getDepartmentId() != null) {
                resp.setDepartment(departmentClient.getDepartmentById(emp.getDepartmentId()));
            }
            return resp;
        }).toList();
    }

    // Create employee and fetch department info
    public EmployeeResponse createEmployee(Employee employee) {
        Employee savedEmployee = employeeRepo.save(employee);
        EmployeeResponse resp = mapper.map(savedEmployee, EmployeeResponse.class);

        if (employee.getDepartmentId() != null) {
            resp.setDepartment(departmentClient.getDepartmentById(employee.getDepartmentId()));
        }

        return resp;
    }

    // Update employee (department ID can be updated if needed)
    public EmployeeResponse updateEmployee(int id, Employee employeeDetails) {
        Employee existing = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existing.setName(employeeDetails.getName());
        existing.setEmail(employeeDetails.getEmail());
        existing.setAge(employeeDetails.getAge());
        existing.setDepartmentId(employeeDetails.getDepartmentId());

        Employee updated = employeeRepo.save(existing);
        EmployeeResponse resp = mapper.map(updated, EmployeeResponse.class);

        if (updated.getDepartmentId() != null) {
            resp.setDepartment(departmentClient.getDepartmentById(updated.getDepartmentId()));
        }

        return resp;
    }

    // Delete employee
    public void deleteEmployee(int id) {
        Employee employee = employeeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employeeRepo.delete(employee);
    }
}
