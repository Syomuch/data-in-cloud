package com.example.services;

import org.springframework.stereotype.Service;

import com.example.models.MovieActorAction;
import com.example.repositories.MovieActorActionRepository;

@Service
public class MovieActorActionServiceImpl implements MovieActorActionService {

    private final MovieActorActionRepository repository;

    public MovieActorActionServiceImpl(MovieActorActionRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(MovieActorAction movieActorAction) {
        repository.save(movieActorAction);
    }
}
