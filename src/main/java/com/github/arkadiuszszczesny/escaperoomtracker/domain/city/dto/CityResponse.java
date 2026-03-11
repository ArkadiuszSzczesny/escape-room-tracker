package com.github.arkadiuszszczesny.escaperoomtracker.domain.city.dto;

import java.util.UUID;

public record CityResponse(
        UUID id,
        String name,
        String voivodeshipName
) {}
