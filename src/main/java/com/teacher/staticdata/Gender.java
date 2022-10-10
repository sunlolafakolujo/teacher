package com.teacher.staticdata;

public enum Gender {
    MALE("MALE"),
    FEMALE("FEMALE"),
    NOT_APPLICABLE("NO APPLICABLE");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Gender{" +
                "gender='" + gender + '\'' +
                '}';
    }
}
