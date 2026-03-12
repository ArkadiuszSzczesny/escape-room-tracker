package com.github.arkadiuszszczesny.escaperoomtracker.domain.room.dto;


import com.github.arkadiuszszczesny.escaperoomtracker.domain.genre.dto.GenreResponse;
import java.util.Set;
import java.util.UUID;

public record EscapeRoomResponse(
        UUID id,
        String name,
        String description,
        Short difficulty,
        Integer durationMinutes,
        String branchAddress,
        String companyName,
        String cityName,
        String voivodeshipName,
        Set<GenreResponse> genres
) {}