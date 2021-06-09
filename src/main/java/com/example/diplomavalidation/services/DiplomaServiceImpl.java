package com.example.diplomavalidation.services;

import com.example.diplomavalidation.exceptions.StudentFailedException;
import com.example.diplomavalidation.requests.DiplomaRequest;
import com.example.diplomavalidation.requests.SubjectRequest;
import com.example.diplomavalidation.responses.DiplomaResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiplomaServiceImpl implements DiplomaService {
    public DiplomaResponse analyzeNotes(DiplomaRequest diplomaRequest){
        Double averageGrade = calculateAverage(diplomaRequest.getSubjects());
        return writeDiploma(diplomaRequest, averageGrade);

    };

    public Double calculateAverage(List<SubjectRequest> grades){
        return Math.round(100*grades.stream()
                .mapToDouble(SubjectRequest::getNote).average().orElse(0))/100.0;
    }

    public DiplomaResponse writeDiploma(DiplomaRequest diplomaRequest, Double averageGrade){
        return new DiplomaResponse(withHonors(averageGrade), diplomaRequest.getName(),
                averageGrade, diplomaRequest.getSubjects());
    }

    public String withHonors(Double averageGrade){
        if (averageGrade >= 9){
            return "Parabéns, sua média é "+ averageGrade;
        } else if (averageGrade > 6){
            return "Sua média é "+averageGrade;
        }
        throw new StudentFailedException("Estudante não possui nota suficiente para obter diploma.");
    }
}
