package com.example.sample2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sample2.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
