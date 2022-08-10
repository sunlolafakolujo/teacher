package com.teacher.staticdata;

public enum MeansOfIdentification {
    NIN("NIN"),
    PASSPORT("PASSPORT"),
    DRIVERS_LICENSE("DRIVERS_LICENSE");

    private final String meansOfIdentification;

    MeansOfIdentification(String meansOfIdentification) {
        this.meansOfIdentification = meansOfIdentification;
    }

    public String getMeansOfIdentification() {
        return meansOfIdentification;
    }

    @Override
    public String toString() {
        return "MeansOfIdentification{" +
                "meansOfIdentification='" + meansOfIdentification + '\'' +
                '}';
    }
}
