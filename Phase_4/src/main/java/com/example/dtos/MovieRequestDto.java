package com.example.dtos;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

public class MovieRequestDto {

    @NotBlank(message = "Name cant be null")
    public String name;

    @NotNull(message = "Date of introduction cant be null")
    public LocalDate introductionDate;

    @NotNull(message = "Budget cant be negative")
    @Min(value = 0, message = "Budget cant be negative")
    public Integer budget;
}
