package com.projects.studentrestapi.service.impl;

import com.projects.studentrestapi.dto.StudentDto;
import com.projects.studentrestapi.entity.Student;
import com.projects.studentrestapi.exception.UserAlreadyExistsException;
import com.projects.studentrestapi.mapper.StudentMapper;
import com.projects.studentrestapi.repository.StudentRepository;
import com.projects.studentrestapi.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public StudentDto saveStudent(StudentDto studentDto) {
        Student student = StudentMapper.INSTANCE.mapToEntity(studentDto);
        Optional<Student> foundStudent = studentRepository
                .findByEmail(student.getEmail());
        if (foundStudent.isPresent()) {
            throw new UserAlreadyExistsException("Student already exists with email : " + student.getEmail());
        }
        Student savedStudent = studentRepository.save(student);
        System.out.println("Saved student from impl" + savedStudent.getEmail());
        return StudentMapper.INSTANCE.mapToDto(savedStudent);
    }

    @Override
    public Student saveStudentEntity(Student student) {
        Optional<Student> findStudent = studentRepository.findByEmail(student.getEmail());
        if (findStudent.isPresent()) {
            throw new UserAlreadyExistsException("Student already exist with email: " + student.getEmail());
        }
        return studentRepository.save(student);
    }
}