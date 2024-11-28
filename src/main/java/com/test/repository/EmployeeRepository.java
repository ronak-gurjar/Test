package com.test.repository;


import com.test.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e JOIN FETCH e.department WHERE e.department.id = :deptId")
    List<Employee> findEmployeesByDepartmentId(@Param("deptId") Long deptId);

    @Query("SELECT e.department.name, COUNT(e) FROM Employee e GROUP BY e.department.name")
    List<Object[]> countEmployeesByDepartment();
}


