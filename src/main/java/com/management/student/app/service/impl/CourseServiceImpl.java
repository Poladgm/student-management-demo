package com.management.student.app.service.impl;

import com.management.student.app.dto.CourseDto;
import com.management.student.app.dto.RegisterStudentDto;
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
import com.management.student.app.service.CourseService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private static final CourseMapper COURSE_MAPPER = CourseMapper.INSTANCE;
    private static final StudentMapper STUDENT_MAPPER = StudentMapper.INSTANCE;
    private static final Logger logger = LogManager.getLogger (CourseServiceImpl.class);

    public CourseServiceImpl(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    public List<CourseDto> findAll() {
        return COURSE_MAPPER.toCourseDtoList (courseRepository.findAll ());
    }


    @Override
    public Set<StudentDto> findStudentListByCourse(String courseNumber) {
        Course course = courseRepository.findByCourseNumber (courseNumber).orElseThrow (() -> new NotFoundException (ErrorCode.ERCNTF11.name (), ErrorCode.ERCNTF11.getValue ()));
        Set<Student> students = studentRepository.findAllByCoursesIn (Stream.of (course).collect (Collectors.toCollection (HashSet::new)));
        logger.info ("{} : student list {}", course, students);
        return STUDENT_MAPPER.toStudentDtoSet (students);
    }

    @Override
    public void addCourse(CourseDto courseDto) {
        checkIfCourseExists (courseDto.getCourseNumber ());
        logger.info ("new course to save {}", courseDto);
        courseRepository.save (COURSE_MAPPER.toCourse (courseDto));
    }

    @Override
    public void registerStudent(String courseNumber, List<RegisterStudentDto> studentDtoSet) {
        Course course = courseRepository.findByCourseNumber (courseNumber).orElseThrow (() -> new NotFoundException (ErrorCode.ERCNTF11.name (), ErrorCode.ERCNTF11.getValue ()));
        Set<Student> students = studentRepository.findAllByStudentNumberIn (studentDtoSet.stream ().map (RegisterStudentDto::getStudentNumber).collect (Collectors.toList ()));
        logger.info ("students to register {}", students);
        course.getStudents ().addAll (students);
        courseRepository.save (course);

    }

    @Override
    public void removeCourse(String courseNumber) {
        Course course = courseRepository.findByCourseNumber (courseNumber).orElseThrow (() -> new NotFoundException (ErrorCode.ERCNTF11.name (), ErrorCode.ERCNTF11.getValue ()));
        logger.info ("course to delete {}", course);
        courseRepository.delete (course);
    }


    private void checkIfCourseExists(String courseNumber) {
        if (courseRepository.existsByCourseNumber (courseNumber)) {
            throw new BadRequestException (ErrorCode.ERCAR12.name (), ErrorCode.ERCAR12.getValue ());
        }
    }
}
