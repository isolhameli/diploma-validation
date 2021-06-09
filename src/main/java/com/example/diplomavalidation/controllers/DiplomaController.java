package com.example.diplomavalidation.controllers;

import com.example.diplomavalidation.requests.DiplomaRequest;
import com.example.diplomavalidation.responses.DiplomaResponse;
import com.example.diplomavalidation.services.DiplomaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class DiplomaController {

    private final DiplomaService diplomaService;

    public DiplomaController(DiplomaService diplomaService) {
        this.diplomaService = diplomaService;
    }

    @PostMapping(value = "/analyzeNotes")
    public ResponseEntity<DiplomaResponse> createDiploma(@RequestBody @Valid DiplomaRequest diplomaRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(diplomaService.analyzeNotes(diplomaRequest));
    }
}
