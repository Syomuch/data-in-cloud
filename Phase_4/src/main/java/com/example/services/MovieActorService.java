package com.example.services;

import java.util.List;

import com.example.dtos.ActorResponseDto;

public interface MovieActorService {
    List<ActorResponseDto> getActorsInMovie(Integer movieId);

    List<ActorResponseDto> addActorToMovie(String actorId, int movieId);

    List<ActorResponseDto> removeActorFromMovie(String actorId, int movieId);
}
