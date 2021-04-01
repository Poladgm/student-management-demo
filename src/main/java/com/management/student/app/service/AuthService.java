package com.management.student.app.service;

import com.management.student.app.dto.AuthToken;
import com.management.student.app.dto.AuthenticationDto;
import com.management.student.app.dto.UserDto;


public interface AuthService {
    AuthToken signIn(AuthenticationDto authenticationDto);
     void signUp(UserDto userDto);
}
