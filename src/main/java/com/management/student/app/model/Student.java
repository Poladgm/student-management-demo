package com.management.student.app.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;

import java.util.Set;


@Entity
@Data
@EqualsAndHashCode(exclude = {"courses"}, callSuper = false)
public class Student extends BaseEntity {
    private String name;
    private String surname;
    @Column(unique = true)
    private String studentNumber;
    @Column(unique = true)
    private String emailAddress;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "students", cascade= CascadeType.ALL)
    private Set<Course> courses;

}
