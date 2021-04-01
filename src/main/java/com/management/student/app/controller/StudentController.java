package com.management.student.app.controller;

import com.management.student.app.dto.CourseDto;
import com.management.student.app.dto.StudentDto;
import com.management.student.app.service.StudentService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("students/")
@ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string")})
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{studentNumber}/courses")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string")})
    public ResponseEntity<Set<CourseDto>> findCourseListByStudent(@PathVariable String studentNumber) {
        return ResponseEntity.ok (studentService.getCourseListByStudent (studentNumber));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string")})
    public ResponseEntity<List<StudentDto>> findStudentList() {
        return ResponseEntity.ok (studentService.findAll ());
    }



    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string")})
    public ResponseEntity<Void> addStudent(@RequestBody StudentDto studentDto) {
        studentService.addStudent (studentDto);
        return ResponseEntity.ok ().build ();
    }

    @DeleteMapping("{studentNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string")})
    public ResponseEntity<Void> removeStudent(@PathVariable String studentNumber) {
        studentService.removeStudent (studentNumber);
        return ResponseEntity.ok ().build ();
    }
}
