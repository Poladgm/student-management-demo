package com.management.student.app.repository;

import com.management.student.app.model.Course;
import com.management.student.app.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByCourseNumber(String courseNumber);

    Optional<Course> findByCourseNumber(String courseNumber);

    Set<Course> findAllByStudentsIn(Set<Student> students);


}
