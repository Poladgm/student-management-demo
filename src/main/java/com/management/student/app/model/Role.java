package com.management.student.app.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;


@Entity
@Data
public class Role extends BaseEntity implements GrantedAuthority {
    private String name;


    @Override
    public String getAuthority() {
        return name;
    }
}
