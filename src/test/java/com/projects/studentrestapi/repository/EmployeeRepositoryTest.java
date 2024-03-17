package com.projects.studentrestapi.repository;

import com.projects.studentrestapi.entity.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EmployeeRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @DisplayName("JUnit test for save student operation")
    @Test
    public void givenStudentObject_whenSave_thenReturnSavedStudent() {
        // given - precondition or setup
        Student student = Student.builder()
                .firstName("John Christopher")
                .lastName("Ilacad")
                .email("johnilacad@sample.com")
                .build();
        // when - action or behaviour that we are going to test
        Student savedStudent = studentRepository.save(student);
        // then - verify the output
        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isGreaterThanOrEqualTo(100);
    }

    @DisplayName("JUnit test for get all students operation")
    @Test
    public void givenStudentList_whenFindAll_thenStudentList() {
        // given - precondition or setup
        Student student1 = Student.builder()
                .firstName("John Christopher")
                .lastName("Ilacad")
                .email("johnilacad@sample.com")
                .build();

        Student student2 = Student.builder()
                .firstName("John")
                .lastName("Cena")
                .email("cena@sample.com")
                .build();

        studentRepository.save(student1);
        studentRepository.save(student2);
        // when - action or behaviour that we are going testing
        List<Student> students = studentRepository.findAll();
        // then - verify the output
        assertThat(students).isNotNull();
        assertThat(students.size()).isEqualTo(2);
    }
}

