package com.management.student.app.repository;

import com.management.student.app.model.Course;
import com.management.student.app.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByStudentNumber(String studentNumber);

    Set<Student> findAllByCoursesIn(Set<Course> courses);

    Optional<Student> findByStudentNumber(String studentNumber);

    Set<Student> findAllByStudentNumberIn(List<String> studentNumberList);
}
