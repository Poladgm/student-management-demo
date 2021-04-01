package com.management.student.app.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
     UserDetails loadUserByUsername(String email);
}
