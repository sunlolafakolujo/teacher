package com.teacher.staticdata;

public enum Status {

    YES("YES"),
    NO("NO");

    private final String current;

    Status(String current) {
        this.current = current;
    }

    public String getCurrent() {
        return current;
    }

    @Override
    public String toString() {
        return "Current{" +
                "current='" + current + '\'' +
                '}';
    }
}
