package com.projects.studentrestapi.service;

import com.projects.studentrestapi.dto.StudentDto;
import com.projects.studentrestapi.entity.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    StudentDto saveStudent(StudentDto studentDto);

    Student saveStudentEntity(Student student);

    List<Student> getAllStudents();

    Optional<Student> getStudentById(long id);

    Student updateStudent(Student student);

    void deleteStudentById(long id);
}
