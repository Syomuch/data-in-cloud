package com.example.dtos;

import java.time.LocalDate;

public class MovieResponseDto {

    private Integer id;

    private String name;

    private LocalDate introductionDate;

    private Integer budget;

    public MovieResponseDto(Integer id, String name, LocalDate presentationDate, Integer budget) {
        this.id = id;
        this.name = name;
        this.introductionDate = presentationDate;
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
