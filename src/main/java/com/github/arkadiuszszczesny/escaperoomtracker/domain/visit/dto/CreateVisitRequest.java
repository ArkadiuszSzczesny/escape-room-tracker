package com.github.arkadiuszszczesny.escaperoomtracker.domain.visit.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CreateVisitRequest(
        @NotNull UUID escapeRoomId,
        @NotNull @PastOrPresent LocalDate playedAt,
        @NotNull Boolean isEscaped,
        Integer escapeTimeSec,
        List<CreateParticipantRequest> participants
) {}