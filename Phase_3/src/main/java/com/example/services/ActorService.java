package com.example.services;

import java.util.List;

import com.example.dtos.ActorRequestDto;
import com.example.dtos.ActorResponseDto;

public interface ActorService {

    List<ActorResponseDto> getAll();

    ActorResponseDto create(ActorRequestDto actorRequestDto);

    ActorResponseDto getById(String id);

    boolean delete(String id);

    ActorResponseDto update(ActorRequestDto actorRequestDto, String id);
}
