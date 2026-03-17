package com.github.arkadiuszszczesny.escaperoomtracker.domain.stats.dto;

public record RoomStatsResponse(
        long totalVisits,
        long escapedCount,
        double escapeRate,
        double avgEscapeTimeSec
) {}
