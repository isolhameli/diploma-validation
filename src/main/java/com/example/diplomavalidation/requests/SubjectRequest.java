package com.example.diplomavalidation.requests;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class SubjectRequest {

    @NotNull
    @Pattern(regexp = "^[\\p{L}\\s]{8,50}",message = "subject name must be only contain letters " +
            "and must be between 8 and 50 characters")
    private String subject;

    @NotNull
    @Range(min=0,max=10,message = "note must be between 0 and 10")
    private Integer note;

    @Deprecated
    public SubjectRequest() {
    }

    public SubjectRequest(String subject, Integer note) {
        this.subject = subject;
        this.note = note;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }
}
