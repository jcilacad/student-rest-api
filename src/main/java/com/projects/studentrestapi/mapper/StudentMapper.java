package com.projects.studentrestapi.mapper;

import com.projects.studentrestapi.dto.StudentDto;
import com.projects.studentrestapi.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    StudentDto mapToDto (Student student);

    Student mapToEntity (StudentDto studentDto);
}
