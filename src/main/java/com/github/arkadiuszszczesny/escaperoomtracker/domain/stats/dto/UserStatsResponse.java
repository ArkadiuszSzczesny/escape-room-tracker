package com.github.arkadiuszszczesny.escaperoomtracker.domain.stats.dto;

import java.util.Map;

public record UserStatsResponse(
        long totalVisits,
        long escapedCount,
        long failedCount,
        double escapeRate,
        double avgEscapeTimeSec,
        Map<String, Long> visitsByGenre,
        Map<Integer, Long> visitsByDifficulty
) {}
