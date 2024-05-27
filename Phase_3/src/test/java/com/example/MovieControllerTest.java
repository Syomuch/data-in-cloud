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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.controllers.MovieController;
import com.example.dtos.MovieResponseDto;
import com.example.exceptions.EntityNotFoundException;
import com.example.handlers.ServerExceptionHandler;
import com.example.models.Movie;
import com.example.services.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
public class MovieControllerTest {

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(movieController)
                .setControllerAdvice(new ServerExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void getAll() throws Exception {
        ArrayList<MovieResponseDto> movies = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            movies.add(new MovieResponseDto(i, "Movie " + i, LocalDate.now().minusYears(i), 1000000));
        }
        String responseMovies = objectMapper.writeValueAsString(movies);
        when(movieService.getAll()).thenReturn(movies);

        mockMvc.perform(get("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseMovies))
                .andExpect(status().isOk());
    }

    @Test
    void getById_ValidId() throws Exception {
        MovieResponseDto responseMovie = new MovieResponseDto(1, "Movie " + 1, LocalDate.now().minusYears(1), 1000000);
        String responseMovieString = objectMapper.writeValueAsString(responseMovie);

        when(movieService.getById(1))
                .thenReturn(responseMovie);

        mockMvc.perform(get("/api/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseMovieString))
                .andExpect(status().isOk());
    }

    @Test
    void getById_InvalidId() throws Exception {
        when(movieService.getById(99999))
                .thenThrow(EntityNotFoundException.class);

        mockMvc.perform(get("/api/movies/99999")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }

    @Test
    void delete_InvalidId() throws Exception {
        when(movieService.delete(99999))
                .thenThrow(EntityNotFoundException.class);

        mockMvc.perform(delete("/api/movies/99999"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof EntityNotFoundException));
    }

    @Test
    void postMovie_InvalidMovie() throws Exception {
        Movie responseMovie = new Movie("Movie " + 1, LocalDate.now().minusYears(1), 1000000);
        String responseMovieString = objectMapper.writeValueAsString(responseMovie);

        mockMvc.perform(post("/api/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseMovieString))
                .andExpect(status().isBadRequest());
    }

    @Test
    void patchMovie_InvalidMovie() throws Exception {
        Movie responseMovie = new Movie("Movie " + 1, LocalDate.now().minusYears(1), 1000000);
        String responseMovieString = objectMapper.writeValueAsString(responseMovie);

        mockMvc.perform(patch("/api/movies/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(responseMovieString))
                .andExpect(status().isBadRequest());
    }
}
