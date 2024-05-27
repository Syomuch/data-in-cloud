package com.example.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.dtos.ActorRequestDto;
import com.example.dtos.ActorResponseDto;
import com.example.exceptions.EntityNotFoundException;
import com.example.mappers.ActorMapper;
import com.example.models.Actor;
import com.example.repositories.ActorRepository;

@Service
public class ActorServiceImpl implements ActorService {

    private final ActorRepository actorRepository;
    private final ActorMapper actorMapper;

    public ActorServiceImpl(ActorRepository actorRepository, ActorMapper actorMapper) {
        this.actorRepository = actorRepository;
        this.actorMapper = actorMapper;
    }

    @Override
    public List<ActorResponseDto> getAll() {
        return actorRepository.findAll()
                .stream()
                .map(actorMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public ActorResponseDto create(ActorRequestDto actorRequestDto) {
        Actor actor = actorMapper.toModel(actorRequestDto);

        return actorMapper.toResponseDto(actorRepository.save(actor));
    }

    @Override
    public ActorResponseDto getById(String id) {
        return actorMapper.toResponseDto(this.findActorBy(id));
    }

    @Override
    public boolean delete(String id) {
        actorRepository.delete(this.findActorBy(id));
        return true;
    }

    @Override
    public ActorResponseDto update(ActorRequestDto actorRequestDto, String id) {
        return actorMapper.toResponseDto(
                actorRepository.findById(id)
                        .map(entity -> {
                            entity.setName(actorRequestDto.name);
                            entity.setDateOfBirth(actorRequestDto.dateOfBirth);
                            entity.setHeight(actorRequestDto.height);
                            return actorRepository.save(entity);
                        })
                        .orElseThrow(() -> new EntityNotFoundException("Actor with id " + id + " not found"))
        );
    }

    private Actor findActorBy(String id) {
        return actorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Actor shop with id " + id + " not found"));
    }
}