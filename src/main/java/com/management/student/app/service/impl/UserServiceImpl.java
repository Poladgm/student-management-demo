package com.management.student.app.service.impl;

import com.management.student.app.enumeration.ErrorCode;
import com.management.student.app.exception.BadRequestException;
import com.management.student.app.model.User;
import com.management.student.app.repository.UserRepository;
import com.management.student.app.security.SecurityUser;
import com.management.student.app.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service(value = "userService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository
                .findByEmailAddress (email)
                .orElseThrow (() -> new BadRequestException (ErrorCode.ERREG20.name (), ErrorCode.ERREG20.getValue ()));
        return buildSecurityUser (user);
    }

    private SecurityUser buildSecurityUser(User user) {
        return SecurityUser.builder ()
                .username (user.getEmailAddress ())
                .password (user.getPassword ())
                .authorities (new ArrayList<> (user.getRoles ()))
                .accountNonExpired (true)
                .accountNonLocked (true)
                .credentialsNonExpired (true)
                .enabled (true).build ();
    }


}

