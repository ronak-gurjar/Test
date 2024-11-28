package com.test.entity;


import jakarta.persistence.Entity;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Developer extends Employee {

    private String programmingLanguage;

    public Developer(String name, Double salary, Department department, String programmingLanguage) {
        this.setName(name);
        this.setSalary(salary);
        this.setDepartment(department);
        this.programmingLanguage = programmingLanguage;
    }

    @Override
    public String toString() {
        return super.toString() + ", programmingLanguage='" + programmingLanguage + '\'';
    }
}


