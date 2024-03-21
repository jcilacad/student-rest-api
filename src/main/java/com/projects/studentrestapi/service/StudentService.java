package com.projects.studentrestapi.service;

import com.projects.studentrestapi.dto.StudentDto;
import com.projects.studentrestapi.entity.Student;

public interface StudentService {
    StudentDto saveStudent(StudentDto studentDto);

    Student saveStudentEntity(Student student);
}
