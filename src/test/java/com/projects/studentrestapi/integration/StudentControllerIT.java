package com.projects.studentrestapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.studentrestapi.entity.Student;
import com.projects.studentrestapi.repository.StudentRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentControllerIT extends AbstractContainerBaseTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        studentRepository.deleteAll();
    }

    @Test
    void givenStudentObject_whenCreateStudent_thenReturnStudentObject() throws Exception {

        // given
        Student student = Student.builder()
                .firstName("John Christopher")
                .lastName("Ilacad")
                .email("jcdilacad2020@plm.edu.ph")
                .build();

        // then
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        // when
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(student.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(student.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(student.getEmail())));
    }

    @Test
    void givenStudentList_whenGetAllStudents_thenReturnListOfStudents() throws Exception {

        // given
        List<Student> students = new ArrayList<>();
        students.add(Student.builder().firstName("John").lastName("Cena").email("jcena@gmail.com").build());
        students.add(Student.builder().firstName("Paul").lastName("John").email("pj@gmail.com").build());
        studentRepository.saveAll(students);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students"));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(students.size())));
    }

    // Positive
    @Test
    void givenStudentId_whenGetStudentById_thenReturnStudentObject() throws Exception {

        // given
        Student student = Student.builder()
                .firstName("John Christopher")
                .lastName("Ilacad")
                .email("jc@gmail.com")
                .build();

        Student savedStudent = studentRepository.save(student);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students/{id}",
                savedStudent.getId()));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(savedStudent.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(savedStudent.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(savedStudent.getEmail())));
    }

    // Negative
    @Test
    void givenInvalidStudentId_whenGetStudentById_thenReturnException() throws Exception {

        // given
        long invalidStudentId = 99L;
        Student student = Student.builder()
                .firstName("John Christopher")
                .lastName("Ilacad")
                .email("jc@gmail.com")
                .build();

        studentRepository.save(student);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/students/{id}",
                invalidStudentId));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    // Positive
    @Test
    void givenStudentId_whenUpdateStudent_thenReturnUpdatedStudent() throws Exception {

        // given
        Student student = Student.builder()
                .firstName("John Christopher")
                .lastName("Ilacad")
                .email("jc@gmail.com")
                .build();

        Student updatedStudent = Student.builder()
                .firstName("John Paul")
                .lastName("Cena")
                .email("jpc@gmail.com")
                .build();

        Student savedStudent = studentRepository.save(student);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/students/{id}",
                savedStudent.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedStudent)));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is(updatedStudent.getFirstName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is(updatedStudent.getLastName())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(updatedStudent.getEmail())));
    }

    // Negative
    @Test
    void givenInvalidStudentId_whenUpdateStudent_thenReturnUpdatedStudent() throws Exception {

        // given
        long invalidStudentId = 99L;
        Student student = Student.builder()
                .firstName("John Christopher")
                .lastName("Ilacad")
                .email("jc@gmail.com")
                .build();

        Student updatedStudent = Student.builder()
                .firstName("John Paul")
                .lastName("Cena")
                .email("jpc@gmail.com")
                .build();

        Student savedStudent = studentRepository.save(student);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/students/{id}",
                invalidStudentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedStudent)));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void givenStudentId_whenDeleteStudent_thenReturnSuccess() throws Exception {

        // given
        Student student = Student.builder()
                .firstName("John Christopher")
                .lastName("Ilacad")
                .email("jc@gmail.com")
                .build();

        Student savedStudent = studentRepository.save(student);

        // when
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/students/{id}",
                savedStudent.getId()));

        // then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
