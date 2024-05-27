package com.example.services;

import java.util.List;

import com.example.dtos.MovieRequestDto;
import com.example.dtos.MovieResponseDto;

public interface MovieService {

    List<MovieResponseDto> getAll();

    MovieResponseDto create(MovieRequestDto movieRequestDto);

    MovieResponseDto getById(int id);

    boolean delete(int id);

    MovieResponseDto update(MovieRequestDto movieRequestDto, int id);
}
