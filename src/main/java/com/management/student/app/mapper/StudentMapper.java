package com.management.student.app.mapper;

import com.management.student.app.dto.StudentDto;
import com.management.student.app.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;


@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper (StudentMapper.class);

    Student toStudent(StudentDto studentDto);
    List<StudentDto> toStudentDtoList(List<Student> studentList);
    Set<StudentDto> toStudentDtoSet (Set<Student> studentList);
}
