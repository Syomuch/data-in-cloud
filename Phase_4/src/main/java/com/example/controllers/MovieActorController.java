package com.example.controllers;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.ActorResponseDto;
import com.example.models.MovieActorAction;
import com.example.services.MovieActorService;

@RestController()
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieActorController {

    static final String exchangeName = "MyExchange";
    private final RabbitTemplate rabbitTemplate;
    private final MovieActorService movieActorService;

    public MovieActorController(RabbitTemplate rabbitTemplate, MovieActorService movieActorService) {
        this.rabbitTemplate = rabbitTemplate;
        this.movieActorService = movieActorService;
    }

    @GetMapping("/{movieId}/actors")
    public MovieActorResponse getActorsInMovie(@PathVariable int movieId) {
        return new MovieActorResponse(movieId, movieActorService.getActorsInMovie(movieId));
    }

    @PostMapping("/{movieId}/actors/{actorId}")
    public MovieActorResponse addActorToMovie(@PathVariable int movieId,
            @PathVariable String actorId) {
        MovieActorResponse movieActorResponse = new MovieActorResponse(movieId,
                movieActorService.addActorToMovie(actorId, movieId));
        MovieActorAction action = new MovieActorAction(movieId, actorId, "SAVE");
        rabbitTemplate.convertAndSend(exchangeName, "first.key", action);
        return movieActorResponse;
    }

    @DeleteMapping("/{movieId}/actors/{actorId}")
    public MovieActorResponse removeActorsInMovie(@PathVariable int movieId,
            @PathVariable String actorId) {
        MovieActorResponse movieActorResponse = new MovieActorResponse(movieId,
                movieActorService.removeActorFromMovie(actorId, movieId));
        MovieActorAction action = new MovieActorAction(movieId, actorId, "DELETE");
        rabbitTemplate.convertAndSend(exchangeName, "first.key", action);
        return movieActorResponse;
    }

    public record MovieActorResponse(Integer movieId, List<ActorResponseDto> actors) {
    }
}