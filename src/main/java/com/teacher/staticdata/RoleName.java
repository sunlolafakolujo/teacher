package com.teacher.staticdata;

public enum RoleName {
    TEACHER("TEACHER"),
    PARENT("PARENT"),
    SCHOOL("SCHOOL");

    private final String roleName;

    RoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    @Override
    public String toString() {
        return "RoleName{" +
                "roleName='" + roleName + '\'' +
                '}';
    }
}
