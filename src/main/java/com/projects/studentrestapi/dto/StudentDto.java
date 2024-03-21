package com.projects.studentrestapi.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
}
