package com.github.arkadiuszszczesny.escaperoomtracker.domain.company.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateCompanyRequest(
        @NotBlank String name,
        String website
) {}
