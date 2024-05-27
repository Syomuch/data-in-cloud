package com.example.models;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Name cant be null")
    private String name;

    @NotNull(message = "Date of introduction cant be null")
    private LocalDate introductionDate;

    @NotNull(message = "Budget cant be negative")
    @Min(value = 0, message = "Budget cant be negative")
    private Integer budget;

    public Movie() {
    }

    public Movie(String name, LocalDate introductionDate, Integer budget) {
        this.name = name;
        this.introductionDate = introductionDate;
        this.budget = budget;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getIntroductionDate() {
        return introductionDate;
    }

    public void setIntroductionDate(LocalDate introductionDate) {
        this.introductionDate = introductionDate;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }
}
