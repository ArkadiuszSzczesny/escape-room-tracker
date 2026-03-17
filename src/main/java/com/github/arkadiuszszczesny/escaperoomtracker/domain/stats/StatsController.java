package com.github.arkadiuszszczesny.escaperoomtracker.domain.stats;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.stats.dto.RoomStatsResponse;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.stats.dto.UserStatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
public class StatsController {

    private final StatsService statsService;

    @GetMapping("/me")
    public UserStatsResponse getMyStats(Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        return statsService.getUserStats(userId);
    }

    @GetMapping("/rooms/{roomId}")
    public RoomStatsResponse getRoomStats(@PathVariable UUID roomId) {
        return statsService.getRoomStats(roomId);
    }
}
