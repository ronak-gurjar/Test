package com.test.service;

import com.test.entity.Employee;
import com.test.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService<Employee> {

    private final EmployeeRepository repository;


    public EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Employee save(Employee employee) {
        return repository.save(employee);
    }

    @Override
    public List<Employee> getAll() {
        return repository.findAll();
    }

    @Override
    public Employee getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    @Override
    public Employee update(Long id, Employee entity) {
        Employee existing = getById(id);
        existing.setName(entity.getName());
        existing.setSalary(entity.getSalary());
        return repository.save(existing);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Employee> getEmployeesByDepartment(Long deptId) {
        return repository.findEmployeesByDepartmentId(deptId);
    }

    @Override
    public List<Map<String, Object>> getEmployeeCountByDepartment() {
        return repository.countEmployeesByDepartment().stream()
                .map(record -> Map.of("department", record[0], "count", record[1]))
                .collect(Collectors.toList());
    }
}

