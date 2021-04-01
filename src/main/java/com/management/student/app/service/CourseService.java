package com.management.student.app.service;

import com.management.student.app.dto.CourseDto;
import com.management.student.app.dto.RegisterStudentDto;
import com.management.student.app.dto.StudentDto;

import java.util.List;
import java.util.Set;


public interface CourseService {
    void removeCourse(String courseNumber);

    void addCourse(CourseDto courseDto);

    List<CourseDto> findAll();
    void registerStudent(String courseNumber, List<RegisterStudentDto> studentDtoSet);
    Set<StudentDto> findStudentListByCourse(String courseNumber);

}
