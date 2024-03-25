package com.projects.studentrestapi.service;

import com.projects.studentrestapi.entity.Student;
import com.projects.studentrestapi.exception.UserAlreadyExistsException;
import com.projects.studentrestapi.repository.StudentRepository;
import com.projects.studentrestapi.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;

    @BeforeEach
    public void setup() {
        student = Student.builder()
                .firstName("John Christopher")
                .lastName("Ilacad")
                .email("jcdilacad2020@plm.edu.ph")
                .build();
    }

    @DisplayName("JUnit test for saveStudent() method")
    @Test
    public void givenStudentObject_whenSaveStudent_thenReturnStudentObjectEntity() {

        // given
        given(studentRepository.findByEmail(student.getEmail())).willReturn(Optional.empty());
        given(studentRepository.save(student)).willReturn(student);

        // when
        Student savedStudent = studentService.saveStudent(student);

        // then
        assertThat(savedStudent).isNotNull();
    }

    @DisplayName("JUnit test for saveStudent() method which throws exception")
    @Test
    public void givenExistingEmail_whenSaveStudent_thenThrowsException() {

        // given
        given(studentRepository.findByEmail(student.getEmail())).willReturn(Optional.of(student));

        // when
        assertThrows(UserAlreadyExistsException.class, () -> studentService.saveStudent(student));

        // then
        verify(studentRepository, never()).save(any(Student.class));
    }

    @DisplayName("JUnit test for getAllStudents() method [Positive Scenario]")
    @Test
    public void givenStudentList_whenGetAllStudents_thenReturnStudentList() {

        // given
        Student student1 = Student.builder()
                .id(101L)
                .firstName("John Paul")
                .lastName("Ilacad")
                .email("jpilacad@gmail.com")
                .build();

        given(studentRepository.findAll()).willReturn(List.of(student, student1));

        // when
        List<Student> students = studentService.getAllStudents();

        // then
        assertThat(students).isNotNull();
        assertThat(students.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for getAllStudents() method [Negative scenario]")
    @Test
    public void givenEmptyStudentList_whenGetAllStudents_thenReturnEmptyStudentList() {

        // given
        given(studentRepository.findAll()).willReturn(Collections.emptyList());

        // when
        List<Student> students = studentService.getAllStudents();

        // then
        assertThat(students).isEmpty();
        assertThat(students.size()).isEqualTo(0);
    }

    @DisplayName("JUnit test for getStudentById() method")
    @Test
    public void givenStudentId_whenFindById_thenReturnStudentObject() {

        // given
        given(studentRepository.findById(100L)).willReturn(Optional.of(student));

        // when
        Optional<Student> existingStudent = studentService.getStudentById(100L);

        // then
        assertThat(existingStudent.get()).isEqualTo(student);
        assertThat(existingStudent).isNotNull();
    }

    @DisplayName("JUnit test for updateStudent() method")
    @Test
    public void givenStudentObject_whenUpdateStudent_thenReturnUpdatedStudent() {

        // given
        given(studentRepository.save(student)).willReturn(student);
        student.setEmail("johnchristopherilacad27@gmail.com");
        student.setFirstName("John Cena");

        // when
        Student updatedStudent = studentService.updateStudent(student);

        // then
        assertThat(updatedStudent.getEmail()).isEqualTo("johnchristopherilacad27@gmail.com");
        assertThat(updatedStudent.getFirstName()).isEqualTo("John Cena");
    }

    @DisplayName("JUnit test for deleteStudentById() method")
    @Test
    public void givenStudentId_whenDeleteStudentById_thenNothing() {

        // given
        long studentId = 100L;
        willDoNothing().given(studentRepository).deleteById(studentId);

        // when
        studentService.deleteStudentById(studentId);

        // then
        verify(studentRepository, times(1)).deleteById(studentId);
    }
}
