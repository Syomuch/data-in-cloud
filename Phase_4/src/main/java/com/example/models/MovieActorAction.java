package com.example.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class MovieActorAction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "MovieId is required")
    @Min(value = 0, message = "MovieId must be positive")
    private Integer movieId;

    @NotBlank(message = "ActorId is required")
    private String actorId;

    @NotBlank(message = "Action is required")
    private String action;

    @CreationTimestamp
    private LocalDate createdAt;

    public MovieActorAction(Integer movieId, String actorId, String action) {
        this.movieId = movieId;
        this.actorId = actorId;
        this.action = action;
    }

    public MovieActorAction() {
    }

    @Override
    public String toString() {
        return "MovieActorAction{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", actorId='" + actorId + '\'' +
                ", action='" + action + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
