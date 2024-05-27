package com.example.dtos;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

public class ActorRequestDto {

    @NotBlank(message = "Actor name is required")
    public String name;

    @NotNull(message = "Height is required or 0")
    @Min(value = 0, message = "height can`t have negative amount")
    public Double height;

    @PastOrPresent
    public LocalDate dateOfBirth;

}
