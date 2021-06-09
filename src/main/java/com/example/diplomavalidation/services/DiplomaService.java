package com.example.diplomavalidation.services;

import com.example.diplomavalidation.requests.DiplomaRequest;
import com.example.diplomavalidation.responses.DiplomaResponse;
import org.springframework.http.ResponseEntity;

public interface DiplomaService {
    DiplomaResponse createDiploma(DiplomaRequest diplomaRequest);
}
