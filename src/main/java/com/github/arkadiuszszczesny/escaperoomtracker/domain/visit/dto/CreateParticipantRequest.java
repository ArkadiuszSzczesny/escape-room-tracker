package com.github.arkadiuszszczesny.escaperoomtracker.domain.visit.dto;

import java.util.UUID;

public record CreateParticipantRequest(
        UUID companionUserId,
        String unregisteredName
) {}
