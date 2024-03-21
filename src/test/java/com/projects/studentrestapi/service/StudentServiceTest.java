package com.projects.studentrestapi.service;

import com.projects.studentrestapi.dto.StudentDto;
import com.projects.studentrestapi.entity.Student;
import com.projects.studentrestapi.exception.UserAlreadyExistsException;
import com.projects.studentrestapi.mapper.StudentMapper;
import com.projects.studentrestapi.repository.StudentRepository;
import com.projects.studentrestapi.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;

    @BeforeEach
    public void setup() {
        student = Student.builder()
                .id(100L)
                .firstName("John Christopher")
                .lastName("Ilacad")
                .email("jcdilacad2020@plm.edu.ph")
                .build();
    }

    // TODO: Find a reference why using mapper didn't work on this
    @Test
    public void givenStudentObject_whenSaveStudent_thenReturnStudentObject() {
        // given - precondition or setup
        StudentDto studentDto = StudentDto.builder()
                .id(100L)
                .firstName("John Christopher")
                .lastName("Ilacad")
                .email("jcdilacad2020@plm.edu.ph")
                .build();
        Student student = StudentMapper.INSTANCE.mapToEntity(studentDto);
        given(studentRepository.findByEmail(student.getEmail())).willReturn(Optional.empty());
        given(studentRepository.save(student)).willReturn(student);
        System.out.println(student.getLastName());
        // when - action or behaviour that we are going to test
        StudentDto savedStudent = studentService.saveStudent(studentDto);
        System.out.println("Email is: " + savedStudent.getEmail());
        // then - verify the output
        assertThat(savedStudent).isNotNull();
    }

    @DisplayName("JUnit test for saveStudent() method")
    @Test
    public void givenStudentObject_whenSaveStudent_thenReturnStudentObjectEntity() {
        // given - precondition or setup
        given(studentRepository.findByEmail(student.getEmail())).willReturn(Optional.empty());
        given(studentRepository.save(student)).willReturn(student);
        // when - action or behaviour that we are going to test
        Student savedStudent = studentService.saveStudentEntity(student);
        // then - verify the output
        assertThat(savedStudent).isNotNull();
    }

    @DisplayName("JUnit test for saveStudent() method which throws exception")
    @Test
    public void givenExistingEmail_whenSaveStudent_thenThrowsException() {
        // given - precondition or setup
        given(studentRepository.findByEmail(student.getEmail())).willReturn(Optional.of(student));
        // when - action or behaviour that we are going to test
        assertThrows(UserAlreadyExistsException.class, () -> studentService.saveStudentEntity(student));
        // then - verify the output
        verify(studentRepository, never()).save(any(Student.class));
    }
}
