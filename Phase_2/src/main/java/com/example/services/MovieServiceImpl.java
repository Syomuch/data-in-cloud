package com.example.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.dtos.MovieRequestDto;
import com.example.dtos.MovieResponseDto;
import com.example.exceptions.EntityNotFoundException;
import com.example.mappers.MovieMapper;
import com.example.models.Movie;
import com.example.repositories.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    @Override
    public List<MovieResponseDto> getAll() {
        List<MovieResponseDto> movies = movieRepository.findAll().stream().map(movieMapper::toResponseDto).collect(
                Collectors.toList());
        return movies;
    }

    @Override
    public MovieResponseDto create(MovieRequestDto movieRequestDto) {
        MovieResponseDto movie = movieMapper.toResponseDto(
                movieRepository.save(movieMapper.toModel(movieRequestDto)));
        return movie;
    }

    @Override
    public MovieResponseDto getById(int id) {
        MovieResponseDto movie = movieMapper.toResponseDto(findMovie(id));
        return movie;
    }

    @Override
    public boolean delete(int id) {
        return movieRepository.deleteById(id);
    }

    @Override
    public MovieResponseDto update(MovieRequestDto movieRequestDto, int id) {
        return movieMapper.toResponseDto(movieRepository.findById(id).map(entity -> {
            entity.setName(movieRequestDto.name);
            entity.setBudget(movieRequestDto.budget);
            entity.setIntroductionDate(movieRequestDto.introductionDate);
            return movieRepository.save(entity);
        }).orElseThrow(() -> new EntityNotFoundException("Movie with id " + id + " not found")));
    }

    private Movie findMovie(int id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Movie with id " + id + " not found"));
        return movie;

    }
}
