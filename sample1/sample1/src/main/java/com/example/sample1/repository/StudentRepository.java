package com.example.sample1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.sample1.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
