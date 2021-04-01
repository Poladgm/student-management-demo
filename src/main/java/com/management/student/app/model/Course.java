package com.management.student.app.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;


@Entity
@Data
@EqualsAndHashCode(exclude = {"students"}, callSuper = false)
public class Course extends BaseEntity {
    private String name;
    @Column(unique = true)
    private String courseNumber;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "STUDENT_COURSE", joinColumns = {
            @JoinColumn(name = "COURSE_ID", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "STUDENT_ID", nullable = false, updatable = false)}
    )
    private Set<Student> students;

}
