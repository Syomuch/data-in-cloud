package com.example;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.models.MovieActorAction;
import com.example.repositories.MovieActorActionRepository;
import com.example.services.MovieActorActionServiceImpl;

@SpringBootTest
public class MovieActorActionServiceTest {
    @Mock
    private MovieActorActionRepository repository;

    @InjectMocks
    private MovieActorActionServiceImpl service;

    @Test
    public void testSave() {
        MovieActorAction action = new MovieActorAction();
        service.save(action);
        verify(repository, times(1)).save(action);
    }
}
