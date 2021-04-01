package com.management.student.app.enumeration;

public enum ErrorCode {
    ERSNFD10 ("Student not found"),
    ERCNTF11("Course not found"),
    ERCAR12("Course already exists"),
    ERSDT13 ("Student already exists"),
    ERUAE10("This user already exists"),
    ERREG20("No such email is registered"),
    ERRLOG3("Login or password not correct.Please try again.");



    ErrorCode(String value) {
        this.value = value;
    }

    private final String value;

    public String getValue() {
        return value;
    }
}
