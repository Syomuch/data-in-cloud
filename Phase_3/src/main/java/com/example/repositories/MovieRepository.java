package com.example.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    boolean deleteById(int id);
}
