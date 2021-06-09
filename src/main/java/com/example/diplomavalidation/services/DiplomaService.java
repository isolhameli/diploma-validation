package com.example.diplomavalidation.services;

import com.example.diplomavalidation.requests.DiplomaRequest;
import com.example.diplomavalidation.requests.SubjectRequest;
import com.example.diplomavalidation.responses.DiplomaResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface DiplomaService {
    DiplomaResponse analyzeNotes(DiplomaRequest diplomaRequest);
    Double calculateAverage(List<SubjectRequest> grades);
    DiplomaResponse writeDiploma(DiplomaRequest diplomaRequest, Double averageGrade);
    String withHonors(Double averageGrade);
}
