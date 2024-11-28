package com.test.controller;


import com.test.entity.Employee;
import com.test.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService<Employee> service;

    public EmployeeController(EmployeeService<Employee> service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody Employee employee) {
        return ResponseEntity.ok(service.save(employee));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> update(@PathVariable Long id, @RequestBody Employee employee) {
        return ResponseEntity.ok(service.update(id, employee));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-department/{deptId}")
    public ResponseEntity<?> getEmployeesByDepartment(@PathVariable Long deptId) {
        return ResponseEntity.ok(service.getEmployeesByDepartment(deptId));
    }

    @GetMapping("/count-by-department")
    public ResponseEntity<?> getEmployeeCountByDepartment() {
        return ResponseEntity.ok(service.getEmployeeCountByDepartment());
    }

}
