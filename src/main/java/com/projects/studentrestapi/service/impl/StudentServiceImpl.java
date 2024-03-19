package com.projects.studentrestapi.service.impl;

import com.projects.studentrestapi.dto.StudentDto;
import com.projects.studentrestapi.entity.Student;
import com.projects.studentrestapi.exception.ResourceNotFoundException;
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
            throw new ResourceNotFoundException("Student already exists with email : " + student.getEmail());
        }
        return StudentMapper.INSTANCE.mapToDto(studentRepository.save(student));
    }
}