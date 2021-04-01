package com.management.student.app.mapper;

import com.management.student.app.dto.CourseDto;
import com.management.student.app.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;


@Mapper
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper (CourseMapper.class);

    Course toCourse(CourseDto courseDto);
    CourseDto toCourseDto(Course course);
    List<CourseDto> toCourseDtoList(List<Course> courseList);
    Set<CourseDto> toCourseDtoSet(Set<Course> courseList);

}
