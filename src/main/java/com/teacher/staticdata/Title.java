package com.teacher.staticdata;

public enum Title {

    MR("MR"),
    MRS("MRS"),
    NOT_APPLICABLE("NOT APPLICABLE");

    private final String title;

    Title(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Title{" +
                "title='" + title + '\'' +
                '}';
    }
}
