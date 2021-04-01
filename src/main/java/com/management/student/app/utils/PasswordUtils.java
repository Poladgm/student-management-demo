package com.management.student.app.utils;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {
    private PasswordUtils() {
    }
    public static String encodePassword(String password) {
        return new BCryptPasswordEncoder ().encode (password);
    }
}
