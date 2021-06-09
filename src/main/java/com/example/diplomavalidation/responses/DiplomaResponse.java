package com.example.diplomavalidation.responses;

import com.example.diplomavalidation.requests.SubjectRequest;

import java.util.List;

public class DiplomaResponse {

    private String name;
    private Double average;
    private List<SubjectRequest> subjects;

    public DiplomaResponse(String name, Double average, List<SubjectRequest> subjects) {
        this.name = name;
        this.average = average;
        this.subjects = subjects;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public List<SubjectRequest> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectRequest> subjects) {
        this.subjects = subjects;
    }
}
