package com.github.arkadiuszszczesny.escaperoomtracker.domain.branch.dto;

import java.util.UUID;

public record BranchResponse(
        UUID id,
        String address,
        String companyName,
        String cityName,
        String voivodeshipName
) {}
