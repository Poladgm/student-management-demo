package com.management.student.app.service.impl;

import com.management.student.app.dto.CourseDto;
import com.management.student.app.dto.StudentDto;
import com.management.student.app.enumeration.ErrorCode;
import com.management.student.app.exception.BadRequestException;
import com.management.student.app.exception.NotFoundException;
import com.management.student.app.mapper.CourseMapper;
import com.management.student.app.mapper.StudentMapper;
import com.management.student.app.model.Course;
import com.management.student.app.model.Student;
import com.management.student.app.repository.CourseRepository;
import com.management.student.app.repository.StudentRepository;
import com.management.student.app.service.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class StudentServiceImpl implements StudentService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private static final CourseMapper COURSE_MAPPER = CourseMapper.INSTANCE;
    private static final StudentMapper STUDENT_MAPPER = StudentMapper.INSTANCE;
    private static final Logger logger = LogManager.getLogger (StudentServiceImpl.class);

    public StudentServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<StudentDto> findAll() {
        return STUDENT_MAPPER.toStudentDtoList (studentRepository.findAll ());
    }

    @Override
    public Set<CourseDto> getCourseListByStudent(String studentNumber) {
        Student student = studentRepository.findByStudentNumber (studentNumber).orElseThrow (() -> new NotFoundException (ErrorCode.ERSNFD10.name (), ErrorCode.ERSNFD10.getValue ()));
        Set<Course> courseList = courseRepository.findAllByStudentsIn (Stream.of (student).collect (Collectors.toCollection (HashSet::new)));
        logger.info ("{} : course list {}", student, courseList);
        return COURSE_MAPPER.toCourseDtoSet (courseList);
    }


    @Override
    public void removeStudent(String studentNumber) {
        Student student = studentRepository.findByStudentNumber (studentNumber).orElseThrow (() -> new NotFoundException (ErrorCode.ERSNFD10.name (), ErrorCode.ERSNFD10.getValue ()));
        logger.info ("student to delete: {}", student);
        studentRepository.delete (student);
    }

    @Override
    public void addStudent(StudentDto studentDto) {
        checkIfStudentExists (studentDto.getStudentNumber ());
        logger.info ("new student to save: {}", studentDto);
        studentRepository.save (STUDENT_MAPPER.toStudent (studentDto));
    }

    private void checkIfStudentExists(String studentNumber) {
        if (studentRepository.existsByStudentNumber (studentNumber)) {
            throw new BadRequestException (ErrorCode.ERSDT13.name (), ErrorCode.ERSDT13.getValue ());
        }
    }

}
