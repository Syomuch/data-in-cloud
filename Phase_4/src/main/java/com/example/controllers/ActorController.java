package com.example.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.dtos.ActorRequestDto;
import com.example.dtos.ActorResponseDto;
import com.example.services.ActorService;

@RestController()
@RequestMapping(value = "/api/actors", produces = MediaType.APPLICATION_JSON_VALUE)
public class ActorController {
    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }


    @GetMapping()
    public List<ActorResponseDto> getAll() {
        return actorService.getAll();
    }

    @GetMapping("/{id:[a-zA-Z0-9]*}")
    public ActorResponseDto getById(@PathVariable String id) {
        return actorService.getById(id);
    }

    @DeleteMapping("/{id:[a-zA-Z0-9]*}")
    public boolean delete(@PathVariable String id) {
        return actorService.delete(id);
    }

    @PostMapping()
    public ActorResponseDto postActor(@Valid @RequestBody ActorRequestDto actorRequestDto) {
        return actorService.create(actorRequestDto);
    }

    @PatchMapping("/{id:[a-zA-Z0-9]*}")
    public ActorResponseDto delete(@Valid @RequestBody ActorRequestDto actorRequestDto, @PathVariable String id) {
        return actorService.update(actorRequestDto, id);
    }
}
