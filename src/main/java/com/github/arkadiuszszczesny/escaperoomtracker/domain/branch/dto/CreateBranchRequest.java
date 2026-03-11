package com.github.arkadiuszszczesny.escaperoomtracker.domain.branch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record CreateBranchRequest(
        @NotNull UUID companyId,
        @NotNull UUID cityId,
        @NotBlank String address
) {}