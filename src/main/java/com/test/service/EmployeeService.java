package com.test.service;


import com.test.entity.Employee;

import java.util.List;
import java.util.Map;

public interface EmployeeService<T> {
    T save(Employee employee);

    List<T> getAll();

    T getById(Long id);

    T update(Long id, T entity);

    void delete(Long id);

    List<Map<String, Object>> getEmployeeCountByDepartment();

    List<Employee> getEmployeesByDepartment(Long deptId);


}

