package com.projects.studentrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.studentrestapi.entity.Student;
import com.projects.studentrestapi.exception.ResourceNotFoundException;
import com.projects.studentrestapi.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest
public class StudentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private Student student;

    @BeforeEach
    public void setup() {
        student = Student.builder()
                .firstName("John Christopher")
                .lastName("Ilacad")
                .email("johnchristopher@gmail.com")
                .build();
    }

    @DisplayName("JUnit test for createStudent() method")
    @Test
    public void givenStudentObject_whenSaveStudent_thenReturnStudentObject() throws Exception {

        // given
        given(studentService.saveStudent(any(Student.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when
        ResultActions response = mockMvc.perform(post("/api/v1/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)));

        // then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(student.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(student.getLastName())))
                .andExpect(jsonPath("$.email", is(student.getEmail())));
    }

    @DisplayName("JUnit test for getAllStudents() method")
    @Test
    public void givenListOfStudents_whenGetAllStudents_thenReturnListOfStudents() throws Exception {

        // given
        List<Student> students = new ArrayList<>();
        students.add(student);
        students.add(Student.builder()
                .firstName("John")
                .lastName("Cena")
                .email("jcena2020@email.com")
                .build());
        given(studentService.getAllStudents()).willReturn(students);

        // when
        ResultActions response = mockMvc.perform(get("/api/v1/students"));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(students.size())));
    }


    @DisplayName("JUnit test for getStudentById() method [Positive Scenario]")
    @Test
    public void givenStudentId_whenFindStudentById_thenReturnStudentObject() throws Exception {

        // given
        long id = 100L;
        given(studentService.getStudentById(id)).willReturn(Optional.ofNullable(student));

        // when
        ResultActions response = mockMvc.perform(get("/api/v1/students/{id}", id));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(student.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(student.getLastName())))
                .andExpect(jsonPath("$.email", is(student.getEmail())));
    }


    @DisplayName("JUnit test for getStudentById() method [Negative Scenario]")
    @Test
    public void givenInvalidStudentId_whenGetStudentById_thenReturnExceptionHandler() throws Exception {

        // given
        long studentId = 200L;
        given(studentService.getStudentById(studentId)).willThrow(ResourceNotFoundException.class);

        // when
        ResultActions response = mockMvc.perform(get("/api/v1/students/{id}", studentId));

        // then
        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("JUnit test for updateStudent() method [Positive Scenario]")
    @Test
    public void givenStudentIdAndUpdatedStudentObject_whenUpdateStudent_thenReturnUpdatedStudent() throws Exception {

        // given
        long studentId = 100L;
        Student updatedStudent = Student.builder()
                .firstName("John")
                .lastName("Cena")
                .email("jcena@gmail.com")
                .build();
        given(studentService.getStudentById(studentId)).willReturn(Optional.of(student));
        given(studentService.updateStudent(any(Student.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        ResultActions response = mockMvc.perform(put("/api/v1/students/{id}", studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedStudent)));

        // then
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(updatedStudent.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedStudent.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedStudent.getEmail())));
    }

    @DisplayName("JUnit test for updateStudent() method [Negative Scenario]")
    @Test
    public void givenInvalidStudentId_whenUpdateStudent_thenThrowStudentNotFound() throws Exception {

        // given
        long studentId = 100L;
        Student updatedStudent = Student.builder()
                .firstName("John")
                .lastName("Cena")
                .email("jcena@gmail.com")
                .build();

        given(studentService.getStudentById(studentId)).willReturn(Optional.empty());
        given(studentService.updateStudent(any(Student.class))).willAnswer(invocation -> invocation.getArgument(0));

        // when
        ResultActions response = mockMvc.perform(put("/api/v1/students/{id}", studentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedStudent)));

        // then
        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("JUnit test for deleteStudent() method")
    @Test
    public void givenStudentId_whenDeleteStudent_thenDoNothing() throws Exception {

        // given
        long studentId = 100L;
        willDoNothing().given(studentService).deleteStudentById(studentId);

        // when
        ResultActions response = mockMvc.perform(delete("/api/v1/students/{id}", studentId));

        // then
        response.andDo(print())
                .andExpect(status().isOk());
    }
}
