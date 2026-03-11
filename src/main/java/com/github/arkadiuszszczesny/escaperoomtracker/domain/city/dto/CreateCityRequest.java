package com.github.arkadiuszszczesny.escaperoomtracker.domain.city.dto;


import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record CreateCityRequest(
        @NotBlank String name,
        UUID voivodeshipId
) {}
