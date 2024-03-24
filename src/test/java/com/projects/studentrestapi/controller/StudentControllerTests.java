package com.projects.studentrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.studentrestapi.entity.Student;
import com.projects.studentrestapi.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void givenStudentObject_whenSaveStudent_thenReturnStudentObject() throws Exception {

        // given
        Student student = Student.builder()
                .firstName("John Christopher")
                .lastName("Ilacad")
                .email("jcdilacd2020@plm.edu.ph")
                .build();

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

    @Test
    public void givenListOfStudents_whenGetAllStudents_thenReturnListOfStudents() throws Exception {

        // given
        List<Student> students = new ArrayList<>();
        students.add(Student.builder()
                .id(100L)
                .firstName("John Christopher")
                .lastName("Ilacad")
                .email("jcdilacad@email.com")
                .build());

        students.add(Student.builder()
                .id(101L)
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
}
