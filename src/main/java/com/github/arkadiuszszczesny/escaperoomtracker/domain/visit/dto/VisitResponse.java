package com.github.arkadiuszszczesny.escaperoomtracker.domain.visit.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record VisitResponse(
        UUID id,
        String escapeRoomName,
        String companyName,
        String cityName,
        LocalDate playedAt,
        Boolean isEscaped,
        Integer escapeTimeSec,
        List<ParticipantResponse> participants
) {}