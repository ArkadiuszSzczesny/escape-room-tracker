package com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.security.dto;

public record AuthResponse(
        String token,
        String username,
        String role
) {}
