package com.example;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.controllers.ActorController;
import com.example.dtos.ActorResponseDto;
import com.example.exceptions.EntityNotFoundException;
import com.example.handlers.ServerExceptionHandler;
import com.example.services.ActorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
public class ActorControllerTest {
    @Mock
    private ActorService service;

    @InjectMocks
    private ActorController actorController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(actorController)
                .setControllerAdvice(new ServerExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void getAll() throws Exception {
        List<ActorResponseDto> actorResponseDtos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            actorResponseDtos.add(new ActorResponseDto("someUUIDsomeUUID", "Actor", 178.0, LocalDate.now().minusDays(1)));
        }

        String responseActors = objectMapper.writeValueAsString(actorResponseDtos);
        when(service.getAll())
                .thenReturn(actorResponseDtos);

        mockMvc.perform(get("/api/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseActors))
                .andExpect(status().isOk());
    }

    @Test
    void getById_ValidId() throws Exception {
        ActorResponseDto actorResponseDto = new ActorResponseDto("someUUIDsomeUUID", "Name", 178.0, LocalDate.now().minusDays(1));
        String responseActorDTOJson = objectMapper.writeValueAsString(actorResponseDto);

        when(service.getById("someUUIDsomeUUID"))
                .thenReturn(actorResponseDto);

        mockMvc.perform(get("/api/actors/someUUIDsomeUUID")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseActorDTOJson))
                .andExpect(status().isOk());
    }

    @Test
    void getById_InvalidId() throws Exception {
        when(service.getById("someUUIDsomeUUID"))
                .thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/api/actors/someUUIDsomeUUID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }

    @Test
    void delete_InvalidId() throws Exception {
        when(service.delete("someUUIDsomeUUID"))
                .thenThrow(EntityNotFoundException.class);

        mockMvc.perform(delete("/api/actors/someUUIDsomeUUID"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }

    @Test
    void postActor_InvalidActor() throws Exception {
        mockMvc.perform(post("/api/actors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void patchActor_InvalidActor() throws Exception {
        mockMvc.perform(patch("/api/actors/someUUIDsomeUUID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}
