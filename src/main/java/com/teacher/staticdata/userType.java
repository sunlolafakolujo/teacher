package com.teacher.staticdata;

public enum userType {
    TEACHER("TEACHER"),
    PARENT("PATENT"),
    SCHOOL("SCHOOL");

    private final String userType;

    userType(String userType) {
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
