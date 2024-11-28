package com.test.service;

import com.test.entity.Department;
import com.test.entity.Manager;
import com.test.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class EmployeeServiceTest {

    private EmployeeServiceImpl service;

    private EmployeeRepository repository;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(EmployeeRepository.class);
        service = new EmployeeServiceImpl(repository);
    }

    @Test
    void testSave_Success() {
        Department department = new Department("IT");
        Manager manager = new Manager("John Doe", 80000.0, department, "Tech Team");
        when(repository.save(manager)).thenReturn(manager);
        Manager saved = (Manager) service.save(manager);

        assertNotNull(saved);
        assertEquals("John Doe", saved.getName());
        assertEquals(80000.0, saved.getSalary());
        assertEquals("Tech Team", saved.getTeamName());
        assertEquals("IT", saved.getDepartment().getName());
    }

    @Test
    void testGetById_Failure() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> service.getById(1L));
        assertEquals("Employee not found", exception.getMessage());
    }
}

