package com.github.arkadiuszszczesny.escaperoomtracker.domain.room.dto;


import jakarta.validation.constraints.*;
import java.util.Set;
import java.util.UUID;

public record CreateEscapeRoomRequest(
        @NotBlank String name,
        String description,
        @NotNull @Min(1) @Max(5) Short difficulty,
        @NotNull @Positive Integer durationMinutes,
        @NotNull UUID branchId,
        Set<UUID> genreIds
) {}
