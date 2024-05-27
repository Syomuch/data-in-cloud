package com.example.controllers;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.ActorResponseDto;
import com.example.services.MovieActorService;

@RestController()
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieActorController {

    private final MovieActorService movieActorService;

    public MovieActorController(MovieActorService movieActorService) {
        this.movieActorService = movieActorService;
    }

    @GetMapping("/{movieId}/actors")
    public MovieActorResponse getActorsInMovie(@PathVariable int movieId) {
        return new MovieActorResponse(movieId, movieActorService.getActorsInMovie(movieId));
    }

    @PostMapping("/{movieId}/actors/{actorId}")
    public MovieActorResponse addActorToMovie(@PathVariable int movieId,
            @PathVariable String actorId) {
        return new MovieActorResponse(movieId, movieActorService.addActorToMovie(actorId, movieId));
    }

    @DeleteMapping("/{movieId}/actors/{actorId}")
    public MovieActorResponse removeActorsInMovie(@PathVariable int movieId,
            @PathVariable String actorId) {
        return new MovieActorResponse(movieId, movieActorService.removeActorFromMovie(actorId, movieId));
    }

    public record MovieActorResponse(Integer movieId, List<ActorResponseDto> actors) {
    }
}