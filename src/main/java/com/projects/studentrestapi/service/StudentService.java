package com.projects.studentrestapi.service;

import com.projects.studentrestapi.entity.Student;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student saveStudent(Student student);

    List<Student> getAllStudents();

    Optional<Student> getStudentById(long id);

    Student updateStudent(Student student);

    void deleteStudentById(long id);
}
