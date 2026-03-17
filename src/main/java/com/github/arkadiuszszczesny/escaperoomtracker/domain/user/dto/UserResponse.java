package com.github.arkadiuszszczesny.escaperoomtracker.domain.user.dto;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String email,
        String role
) {}