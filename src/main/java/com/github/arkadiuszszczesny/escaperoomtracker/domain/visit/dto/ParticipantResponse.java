package com.github.arkadiuszszczesny.escaperoomtracker.domain.visit.dto;

import java.util.UUID;

public record ParticipantResponse(
        UUID id,
        String name,
        boolean registered
) {}
