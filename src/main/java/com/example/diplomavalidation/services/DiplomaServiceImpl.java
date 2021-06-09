package com.example.diplomavalidation.services;

import com.example.diplomavalidation.requests.DiplomaRequest;
import com.example.diplomavalidation.requests.SubjectRequest;
import com.example.diplomavalidation.responses.DiplomaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiplomaServiceImpl implements DiplomaService {
    public DiplomaResponse createDiploma(DiplomaRequest diplomaRequest){
        Double averageGrade = calculateAverageGrade(diplomaRequest.getSubjects());
        return new DiplomaResponse(diplomaRequest.getName(),
                averageGrade, diplomaRequest.getSubjects());

    };

    private Double calculateAverageGrade(List<SubjectRequest> grades){
        return Math.round(100*grades.stream()
                .mapToDouble(SubjectRequest::getNote).average().getAsDouble())/100.0;
    }
}
