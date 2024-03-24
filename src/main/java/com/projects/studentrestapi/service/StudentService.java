package com.projects.studentrestapi.service;

import com.projects.studentrestapi.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student saveStudent(Student student);

    List<Student> getAllStudents();

    Student getStudentById(long id);

    Student updateStudent(Student student);

    void deleteStudentById(long id);
}
