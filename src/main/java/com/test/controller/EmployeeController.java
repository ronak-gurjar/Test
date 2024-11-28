package com.test.controller;


import com.test.dto.AddEmployeeReqDTO;
import com.test.entity.Employee;
import com.test.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController extends BaseController {

    private final EmployeeService<Employee> service;

    public EmployeeController(EmployeeService<Employee> service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Employee employee) {
        return okSuccessResponse(service.save(employee), "Employee created successfully");
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        return okSuccessResponse(service.getAll(), "Employee fetch successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Employee employee) {
        return okSuccessResponse(service.update(id, employee), "Employee updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return okSuccessResponse("Employee deleted successfully");
    }

    @GetMapping("/by-department/{deptId}")
    public ResponseEntity<?> getEmployeesByDepartment(@PathVariable Long deptId) {
        return okSuccessResponse(service.getEmployeesByDepartment(deptId), "Employee fetch successfully");
    }

    @GetMapping("/count-by-department")
    public ResponseEntity<?> getEmployeeCountByDepartment() {
        return okSuccessResponse(service.getEmployeeCountByDepartment(), "Employee count by department successfully");
    }

}
