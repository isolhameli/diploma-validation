package com.example.diplomavalidation.controllers;

import com.example.diplomavalidation.requests.SubjectRequest;
import com.example.diplomavalidation.responses.DiplomaResponse;
import com.example.diplomavalidation.services.DiplomaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DiplomaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private DiplomaService service;

    @Test
    public void shouldRequestWithoutNameThrowValidationException() throws Exception {
        //given
        String diplomaRequest = "{\n" +
                "    \"name\":null,\n" +
                "    \"subjects\": [\n" +
                "        {\n" +
                "            \"subject\":\"Ciencia de dados\",\n" +
                "            \"note\":0.4\n" +
                "        },\n" +
                "        {\n" +
                "            \"subject\":\"Orientacao a objetos\",\n" +
                "            \"note\":10\n" +
                "        }\n" +
                "        ]\n" +
                "}";

        //when
        Mockito.when(service.analyzeNotes(Mockito.any())).thenReturn(new DiplomaResponse("Test",
                "Test",1.0, new ArrayList<SubjectRequest>()));


        //then
        this.mockMvc.perform(
                post("/analyzeNotes")
                        .contentType(MediaType.APPLICATION_JSON).content(diplomaRequest))
                .andDo(print())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    public void shouldNormalStudentPass() throws Exception {
        //given
        String diplomaRequest = "{\n" +
                "                \"name\":\"Israel Solha\",\n" +
                "                \"subjects\": [\n" +
                "        {\n" +
                "            \"subject\":\"Ciencia de dados\",\n" +
                "                \"note\":4\n" +
                "        },\n" +
                "        {\n" +
                "            \"subject\":\"Ciencia de dados\",\n" +
                "                \"note\":10\n" +
                "        },\n" +
                "        {\n" +
                "            \"subject\":\"Introdução a programação\",\n" +
                "                \"note\":7\n" +
                "        }\n" +
                "        ]\n" +
                "}";

        //then
        this.mockMvc.perform(
                post("/analyzeNotes")
                        .contentType(MediaType.APPLICATION_JSON).content(diplomaRequest))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldStudentWithNoSubjectsFail() throws Exception {
        //given
        String diplomaRequest = "{\n" +
                "                \"name\":\"Israel Solha\",\n" +
                "                \"subjects\": []\n" +
                "}";

        //then
        this.mockMvc.perform(
                post("/analyzeNotes")
                        .contentType(MediaType.APPLICATION_JSON).content(diplomaRequest))
                .andDo(print())
                .andExpect(status().isPreconditionFailed());
    }
}