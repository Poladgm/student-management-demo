package com.management.student.app.controller;

import com.management.student.app.dto.CourseDto;
import com.management.student.app.dto.RegisterStudentDto;
import com.management.student.app.dto.StudentDto;
import com.management.student.app.service.CourseService;
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
@RequestMapping("courses/")
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("{courseNumber}/students")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string")})
    public ResponseEntity<Void> registerStudent(@PathVariable String courseNumber, @RequestBody List<RegisterStudentDto> students) {
        courseService.registerStudent (courseNumber, students);
        return ResponseEntity.ok ().build ();
    }

    @GetMapping("{courseNumber}/students")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string")})
    public ResponseEntity<Set<StudentDto>> getCourseListByStudent(@PathVariable String courseNumber) {
        return ResponseEntity.ok (courseService.findStudentListByCourse (courseNumber));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string")})
    public ResponseEntity<List<CourseDto>> findCourseList() {
        return ResponseEntity.ok (courseService.findAll ());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string")})
    public ResponseEntity<Void> addCourse(@RequestBody CourseDto courseDto) {
        courseService.addCourse (courseDto);
        return ResponseEntity.ok ().build ();
    }

    @DeleteMapping("{courseNumber}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = "Authorization", dataType = "string")})
    public ResponseEntity<Void> removeCourse(@PathVariable String courseNumber) {
        courseService.removeCourse (courseNumber);
        return ResponseEntity.ok ().build ();
    }

}
