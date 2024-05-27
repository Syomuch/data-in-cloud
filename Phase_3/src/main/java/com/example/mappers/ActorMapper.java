package com.example.mappers;

import org.mapstruct.Mapper;

import com.example.dtos.ActorRequestDto;
import com.example.dtos.ActorResponseDto;
import com.example.models.Actor;

@Mapper(componentModel = "spring")
public interface ActorMapper {

    Actor toModel(ActorRequestDto actorRequestDto);

    ActorResponseDto toResponseDto(Actor actor);
}
