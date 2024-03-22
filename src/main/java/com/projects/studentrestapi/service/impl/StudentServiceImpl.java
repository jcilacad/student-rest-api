package com.projects.studentrestapi.service.impl;

import com.projects.studentrestapi.dto.StudentDto;
import com.projects.studentrestapi.entity.Student;
import com.projects.studentrestapi.exception.ResourceNotFoundException;
import com.projects.studentrestapi.exception.UserAlreadyExistsException;
import com.projects.studentrestapi.mapper.StudentMapper;
import com.projects.studentrestapi.repository.StudentRepository;
import com.projects.studentrestapi.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Override
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    public Optional<Student> getStudentById(long id) {
        return Optional.ofNullable(studentRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudentById(long id) {
        studentRepository.deleteById(id);
    }
}