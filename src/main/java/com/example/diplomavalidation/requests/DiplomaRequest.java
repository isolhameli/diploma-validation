package com.example.diplomavalidation.requests;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

public class DiplomaRequest {

    @NotNull
    @Pattern(regexp = "^[\\p{L}\\s]{8,50}",message = "student name must be only contain letters and must be between 8 and 50 characters")
    private String name;
    @Valid
    @NotEmpty
    private List<SubjectRequest> subjects;

    @Deprecated
    public DiplomaRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubjectRequest> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<SubjectRequest> subjects) {
        this.subjects = subjects;
    }
}
