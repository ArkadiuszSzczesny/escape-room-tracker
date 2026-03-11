package com.github.arkadiuszszczesny.escaperoomtracker.domain.company.dto;

import java.util.UUID;

public record CompanyResponse(
        UUID id,
        String name,
        String website
) {}
