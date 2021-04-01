package com.management.student.app.service.impl;

import com.management.student.app.dto.AuthToken;
import com.management.student.app.dto.AuthenticationDto;
import com.management.student.app.dto.UserDto;
import com.management.student.app.enumeration.ErrorCode;
import com.management.student.app.exception.BadRequestException;
import com.management.student.app.mapper.UserMapper;
import com.management.student.app.model.User;
import com.management.student.app.repository.UserRepository;
import com.management.student.app.security.TokenProvider;
import com.management.student.app.service.AuthService;
import com.management.student.app.service.AuthorityService;
import com.management.student.app.utils.PasswordUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final AuthorityService roleService;
    private static final UserMapper userMapper = UserMapper.INSTANCE;
    private static final Logger logger = LogManager.getLogger (AuthServiceImpl.class);

    public AuthServiceImpl(AuthenticationManager authenticationManager, TokenProvider tokenProvider, UserRepository userRepository, AuthorityService roleService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Override
    public void signUp(UserDto userDto) {
        checkIfUserExistsByEmail (userDto.getEmailAddress ());
        User user = userMapper.toUser (userDto);
        user.setPassword (PasswordUtils.encodePassword (userDto.getPassword ()));
        user.setRoles (roleService.findAuthorityByEmail (userDto.getEmailAddress ()));
        logger.info ("signup new user {}", user);
        userRepository.save (user);
    }

    @Override
    public AuthToken signIn(AuthenticationDto authenticationDto) {
        logger.info ("sing-in user {}", authenticationDto);
        final Authentication authentication = authenticationManager.authenticate (
                new UsernamePasswordAuthenticationToken (
                        authenticationDto.getEmailAddress (),
                        authenticationDto.getPassword ())
        );
        SecurityContextHolder.getContext ().setAuthentication (authentication);
        final String token = tokenProvider.generateToken (authentication);
        return new AuthToken (token);
    }

    private void checkIfUserExistsByEmail(String email) {
        if (userRepository.existsByEmailAddress (email)) {
            throw new BadRequestException (ErrorCode.ERUAE10.name (), ErrorCode.ERUAE10.getValue ());
        }
    }
}
