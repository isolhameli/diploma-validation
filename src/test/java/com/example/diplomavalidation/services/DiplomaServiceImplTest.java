package com.example.diplomavalidation.services;

import com.example.diplomavalidation.requests.DiplomaRequest;
import com.example.diplomavalidation.requests.SubjectRequest;
import com.example.diplomavalidation.responses.DiplomaResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

public class DiplomaServiceImplTest {

    private final DiplomaService diplomaService = new DiplomaServiceImpl();

    @Test
    public void shouldDiplomaBeValid(){
        // given
        List<SubjectRequest> subjects = new ArrayList<>();
        SubjectRequest subject1 = new SubjectRequest("Ciência de Dados",5);
        SubjectRequest subject2 = new SubjectRequest("Orientação a objetos",8);
        SubjectRequest subject3 = new SubjectRequest("Banco de dados",8);
        subjects.addAll(List.of(subject1,subject2,subject3));
        DiplomaRequest diplomaRequest = new DiplomaRequest("Israel Solha", subjects);

        //when
        DiplomaResponse diplomaResponse = diplomaService.analyzeNotes(diplomaRequest);

        //then
        Assertions.assertEquals(diplomaResponse.getAverage(),7.0);

    }

    @Test
    public void shouldDiplomaBeInvalid(){
        // given
        List<SubjectRequest> subjects = new ArrayList<>();
        SubjectRequest subject1 = new SubjectRequest("Ciência de Dados",6);
        SubjectRequest subject2 = new SubjectRequest("Orientação a objetos",8);
        SubjectRequest subject3 = new SubjectRequest("Banco de dados",8);
        subjects.addAll(List.of(subject1,subject2,subject3));
        DiplomaRequest diplomaRequest = new DiplomaRequest("Israel Solha", subjects);
        //when
        DiplomaResponse diplomaResponse = diplomaService.analyzeNotes(diplomaRequest);

        //then
        Assertions.assertNotEquals(diplomaResponse.getName(), "Israel");

    }

    @Test
    public void shouldCalculateCorrectAverage(){
        //given
        List<SubjectRequest> subjects = new ArrayList<>();
        SubjectRequest subject1 = new SubjectRequest("Ciência de Dados",6);
        SubjectRequest subject2 = new SubjectRequest("Orientação a objetos",8);
        subjects.addAll(List.of(subject1,subject2));

        //when
        Double averageNote = diplomaService.calculateAverage(subjects);

        //then
        Assertions.assertEquals(averageNote,7.0);
    }

    @Test
    public void shouldCalculateWrongAverage(){
        //given
        List<SubjectRequest> subjects = new ArrayList<>();
        SubjectRequest subject1 = new SubjectRequest("Ciência de Dados",6);
        SubjectRequest subject2 = new SubjectRequest("Orientação a objetos",9);
        subjects.addAll(List.of(subject1,subject2));

        //when
        Double averageNote = diplomaService.calculateAverage(subjects);

        //then
        Assertions.assertNotEquals(averageNote,7.0);
    }

    @Test void shouldWriteRightDiploma(){
        //given
        List<SubjectRequest> subjects = new ArrayList<>();
        SubjectRequest subject1 = new SubjectRequest("Ciência de Dados",6);
        SubjectRequest subject2 = new SubjectRequest("Orientação a objetos",8);
        SubjectRequest subject3 = new SubjectRequest("Banco de dados",8);
        subjects.addAll(List.of(subject1,subject2,subject3));
        DiplomaRequest diplomaRequest = new DiplomaRequest("Israel Solha", subjects);

        //when
        DiplomaResponse diplomaResponse = diplomaService.writeDiploma(diplomaRequest,
                diplomaService.calculateAverage(subjects));

        //then
        Assertions.assertEquals(diplomaResponse.getAverage(),7.33);

    }

    @Test void shouldWriteWrongDiploma(){
        //given
        List<SubjectRequest> subjects = new ArrayList<>();
        SubjectRequest subject1 = new SubjectRequest("Ciência de Dados",6);
        SubjectRequest subject2 = new SubjectRequest("Orientação a objetos",8);
        SubjectRequest subject3 = new SubjectRequest("Banco de dados",8);
        subjects.addAll(List.of(subject1,subject2,subject3));
        DiplomaRequest diplomaRequest = new DiplomaRequest("Israel Solha", subjects);

        //when
        DiplomaResponse diplomaResponse = diplomaService.writeDiploma(diplomaRequest,
                diplomaService.calculateAverage(subjects));

        //then
        Assertions.assertNotEquals(diplomaResponse.getAverage(),2);

    }

    @Test void shouldGetHonors(){
        //given
        Double averageGrades = 9.0;

        //when
        String message = diplomaService.withHonors(averageGrades);

        //then
        Assertions.assertEquals("Parabéns, sua média é "+ averageGrades, message);
    }

    @Test void shouldNotGetHonors(){
        //given
        Double averageGrades = 8.0;

        //when
        String message = diplomaService.withHonors(averageGrades);

        //then
        Assertions.assertNotEquals("Parabéns, sua média é "+ averageGrades, message);
    }
}
