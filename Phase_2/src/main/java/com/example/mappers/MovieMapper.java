package com.example.mappers;

import org.mapstruct.Mapper;

import com.example.dtos.MovieRequestDto;
import com.example.dtos.MovieResponseDto;
import com.example.models.Movie;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    Movie toModel(MovieRequestDto movieRequestDto);

    MovieResponseDto toResponseDto(Movie movie);
}
