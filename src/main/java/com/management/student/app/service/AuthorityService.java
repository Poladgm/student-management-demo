package com.management.student.app.service;


import com.management.student.app.model.Role;
import com.management.student.app.model.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;

public interface AuthorityService {
    Set<Role> findAuthorityByEmail(String userEmail);
    Set<SimpleGrantedAuthority> getAuthority(User user);
}
