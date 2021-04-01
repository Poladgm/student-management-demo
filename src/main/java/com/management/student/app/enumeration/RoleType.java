package com.management.student.app.enumeration;

import org.springframework.security.core.GrantedAuthority;

public enum RoleType implements GrantedAuthority {

    ROLE_USER, ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}