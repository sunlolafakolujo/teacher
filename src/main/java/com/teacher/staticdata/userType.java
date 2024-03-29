package com.teacher.staticdata;

public enum UserType {
    TEACHER("TEACHER"),
    PARENT("PARENT"),
    SCHOOL("SCHOOL"),
    EMPLOYEE("EMPLOYEE");

    private final String userType;

    UserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return "userType{" +
                "userType='" + userType + '\'' +
                '}';
    }
}
