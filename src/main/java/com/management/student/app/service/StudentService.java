package com.management.student.app.service;


import com.management.student.app.dto.CourseDto;
import com.management.student.app.dto.StudentDto;

import java.util.List;
import java.util.Set;


public interface StudentService {

    void removeStudent(String studentNumber);
     List<StudentDto> findAll();
    void addStudent(StudentDto studentDto);
    Set<CourseDto> getCourseListByStudent(String studentNumber);

}
