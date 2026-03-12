package com.github.arkadiuszszczesny.escaperoomtracker.domain.genre.dto;

import java.util.UUID;

public record GenreResponse(
        UUID id,
        String name
) {}
