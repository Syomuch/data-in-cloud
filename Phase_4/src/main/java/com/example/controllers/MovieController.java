package com.example.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.MovieRequestDto;
import com.example.dtos.MovieResponseDto;
import com.example.services.MovieService;

@RestController()
@RequestMapping(value = "/api/movies", produces = MediaType.APPLICATION_JSON_VALUE)
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping()
    public List<MovieResponseDto> getAll() {
        return movieService.getAll();
    }

    @GetMapping("/{id}")
    public MovieResponseDto getById(@PathVariable String id) {
        return movieService.getById(Integer.parseInt(id));
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable int id) {
        return movieService.delete(id);
    }

    @PostMapping()
    public MovieResponseDto postMovie(@Valid @RequestBody MovieRequestDto movieRequestDto) {
        return movieService.create(movieRequestDto);
    }

    @PatchMapping("/{id}")
    public MovieResponseDto delete(@Valid @RequestBody MovieRequestDto movieRequestDto, @PathVariable int id) {
        return movieService.update(movieRequestDto, id);
    }

}