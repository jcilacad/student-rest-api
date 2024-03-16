package com.projects.studentrestapi.repository;

import com.projects.studentrestapi.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
