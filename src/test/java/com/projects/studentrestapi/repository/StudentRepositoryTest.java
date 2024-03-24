package com.projects.studentrestapi.repository;

import com.projects.studentrestapi.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    private Student student;


    @BeforeEach
    public void setup() {
        student = Student.builder()
                .firstName("John Christopher")
                .lastName("Ilacad")
                .email("johnilacad@sample.com")
                .build();
    }

    @DisplayName("JUnit test for save student operation")
    @Test
    public void givenStudentObject_whenSave_thenReturnSavedStudent() {
        // given
        // call setup() method
        // when
        Student savedStudent = studentRepository.save(student);
        // then
        assertThat(savedStudent).isNotNull();
        assertThat(savedStudent.getId()).isGreaterThanOrEqualTo(100);
    }

    @DisplayName("JUnit test for get all students operation")
    @Test
    public void givenStudentList_whenFindAll_thenStudentList() {
        // given
        Student student2 = Student.builder()
                .firstName("John")
                .lastName("Cena")
                .email("cena@sample.com")
                .build();
        studentRepository.save(student);
        studentRepository.save(student2);
        // when
        List<Student> students = studentRepository.findAll();
        // then
        assertThat(students).isNotNull();
        assertThat(students.size()).isEqualTo(2);
    }

    @DisplayName("JUnit test for find by id operation")
    @Test
    public void givenStudentObject_whenFindById_thenReturnStudentObject() {
        // given
        Student savedStudent = studentRepository.save(student);
        // when
        Student studentDB = studentRepository.findById(savedStudent.getId()).get();
        // then
        assertThat(studentDB).isNotNull();
    }

    @DisplayName("JUnit test for find by email operation")
    @Test
    public void givenStudentEmail_whenFindByEmail_thenReturnStudentObject() {
        // given
        Student savedStudent = studentRepository.save(student);
        // when
        Student existingStudent = studentRepository.findByEmail(savedStudent.getEmail()).get();
        // then
        assertThat(existingStudent).isNotNull();
    }

    @DisplayName("JUnit test for update student operation")
    @Test
    public void givenStudentObject_whenUpdate_thenReturnStudentObject() {
        // given
        Student savedStudent = studentRepository.save(student);
        // when
        Student existingStudent = studentRepository.findById(savedStudent.getId()).get();
        existingStudent.setEmail("johnchristopherilacad27@gmail.com");
        existingStudent.setFirstName("John Christopher");
        studentRepository.save(existingStudent);
        // then
        assertThat(existingStudent.getEmail()).isEqualTo("johnchristopherilacad27@gmail.com");
        assertThat(existingStudent.getFirstName()).isEqualTo("John Christopher");
    }

    @DisplayName("JUnit test for delete student operation")
    @Test
    public void givenStudentObject_whenDelete_thenRemoveStudent() {
        // given
        Student savedStudent = studentRepository.save(student);
        // when
        studentRepository.deleteById(savedStudent.getId());
        Optional<Student> existingStudent = studentRepository.findById(savedStudent.getId());
        // then
        assertThat(existingStudent).isEmpty();
    }

    @DisplayName("JUnit test for custom query using JPQL (index)")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQL_thenReturnStudentObject() {
        // given
        studentRepository.save(student);
        String firstName = "John Christopher";
        String lastName = "Ilacad";
        // when
        Student existingStudent = studentRepository.findByJPQL(firstName, lastName);
        // then
        assertThat(existingStudent).isNotNull();
    }

    @DisplayName("JUnit test for custom query using JPQL (name params)")
    @Test
    public void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnStudentObject() {
        // given
        studentRepository.save(student);
        String firstName = "John Christopher";
        String lastName = "Ilacad";
        // when
        Student existingStudent = studentRepository.findByJPQLNamedParams(firstName, lastName);
        // then
        assertThat(existingStudent).isNotNull();
    }

    @DisplayName("JUnit test for custom native query using SQL (index)")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeQuery_thenReturnStudentObject() {
        // given
        studentRepository.save(student);
        String firstName = "John Christopher";
        String lastName = "Ilacad";
        // when
        Student existingStudent = studentRepository.findByNativeSQL(firstName, lastName);
        // then
        assertThat(existingStudent).isNotNull();
    }

    @DisplayName("JUnit test for custom native query using SQL (named params)")
    @Test
    public void givenFirstNameAndLastName_whenFindByNativeQueryNamed_thenReturnStudentObject() {
        // given
        studentRepository.save(student);
        String firstName = "John Christopher";
        String lastName = "Ilacad";
        // when
        Student existingStudent = studentRepository.findByNativeSQLNamed(firstName, lastName);
        // then
        assertThat(existingStudent).isNotNull();
    }
}

