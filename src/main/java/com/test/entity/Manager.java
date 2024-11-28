package com.test.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Manager extends Employee {

    private String teamName;

    public Manager(String name, Double salary, Department department, String teamName) {
        this.setName(name);
        this.setSalary(salary);
        this.setDepartment(department);
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return super.toString() + ", teamName='" + teamName + '\'';
    }
}


