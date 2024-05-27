package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.MovieActorAction;

@Repository
public interface MovieActorActionRepository extends JpaRepository<MovieActorAction, Integer> {
}
