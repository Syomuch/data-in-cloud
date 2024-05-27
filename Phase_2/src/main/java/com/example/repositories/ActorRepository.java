package com.example.repositories;

import java.awt.print.Book;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.models.Actor;

@Repository
public interface ActorRepository extends MongoRepository<Actor, String> {
}
