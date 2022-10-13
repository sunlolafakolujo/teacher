package com.teacher.staticdata;

public enum JobType {
    FULL_TIME("Full Time"),
    PART_TIME("Part Time"),
    FULL_TIME_PART_TIME("Full Time, Part Time"),
    HYBRID("Hybrid");

    private final String jobType;

    JobType(String jobType) {
        this.jobType = jobType;
    }

    public String getJobType() {
        return jobType;
    }

    @Override
    public String toString() {
        return "JobType{" +
                "jobType='" + jobType + '\'' +
                '}';
    }
}
