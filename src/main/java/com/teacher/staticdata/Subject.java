package com.teacher.staticdata;

public enum Subject {

    MATHEMATICS("MATHEMATICS"),
    ENGLISH("ENGLISH"),
    BIOLOGY("BIOLOGY"),
    CHEMISTRY("CHEMISTRY"),
    ACCOUNTUNG("ACCOUNTING"),
    PHYSICS("PHYSICS"),
    ADVANCED_MATHIMATICS("ADVANCED MATHEMATICS"),
    AGRICULTURAL_SCIENCE("AGRICULTURAL SCIENCE"),
    TECHNICAL_DRAWING("TECHNICAL DRAWING"),
    SOCIAL_STUDIES("SOCIAL STUDIES"),
    ICT("ICT"),
    FINE_ART("FINE ART"),
    HOME_ECONOMICS("HOME ECONOMICS"),
    ECONOMICS("ECONOMICS");

    private final String subject;

    Subject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subject='" + subject + '\'' +
                '}';
    }
}
