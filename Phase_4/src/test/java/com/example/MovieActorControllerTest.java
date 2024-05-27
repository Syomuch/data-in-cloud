package com.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.controllers.MovieActorController;
import com.example.dtos.ActorResponseDto;
import com.example.handlers.ServerExceptionHandler;
import com.example.services.MovieActorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieActorControllerTest {

    @Mock
    private MovieActorService service;

    @InjectMocks
    private MovieActorController controller;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ServerExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void getAllActorsFromMovie() throws Exception {
        List<ActorResponseDto> actors = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            actors.add(new ActorResponseDto("someUUIDsomeUUID", "Name", 178.0, LocalDate.now().minusDays(1)));
        }
        String actorsDTOList = objectMapper.writeValueAsString(actors);
        when(service.getActorsInMovie(1))
                .thenReturn(actors);



        mockMvc.perform(get("/api/movies/1/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actorsDTOList))
                .andExpect(status().isOk());
    }

    @Test
    void addActorsToMovie() throws Exception {
        List<ActorResponseDto> actors = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            actors.add(new ActorResponseDto("someUUIDsomeUUID", "Name", 178.0, LocalDate.now().minusDays(1)));
        }

        String actorsDTOList = objectMapper.writeValueAsString(actors);
        when(service.addActorToMovie("63a7d215d52900a179fas134", 1))
                .thenReturn(actors);



        mockMvc.perform(post("/api/movies/1/actors/63a7d215d52900a179fas134")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actorsDTOList))
                .andExpect(status().isOk());
    }

    @Test
    void deleteActorsFromMovie() throws Exception {
        List<ActorResponseDto> actors = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            actors.add(new ActorResponseDto("someUUIDsomeUUID", "Name", 178.0, LocalDate.now().minusDays(1)));
        }


        String actorsDTOList = objectMapper.writeValueAsString(actors);
        when(service.removeActorFromMovie("63a7d215d52900a179fas134", 1))
                .thenReturn(actors);



        mockMvc.perform(delete("/api/movies/1/actors/63a7d215d52900a179fas134")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(actorsDTOList))
                .andExpect(status().isOk());

    }
}