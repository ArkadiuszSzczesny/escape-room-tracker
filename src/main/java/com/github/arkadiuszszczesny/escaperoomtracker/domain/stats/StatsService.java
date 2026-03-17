package com.github.arkadiuszszczesny.escaperoomtracker.domain.stats;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.room.EscapeRoomRepository;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.stats.dto.RoomStatsResponse;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.stats.dto.UserStatsResponse;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.visit.Visit;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.visit.VisitRepository;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatsService {

    private final VisitRepository visitRepository;
    private final EscapeRoomRepository escapeRoomRepository;

    public UserStatsResponse getUserStats(UUID userId) {
        var visits = visitRepository.findByUserId(userId);

        long total = visits.size();
        long escaped = visits.stream().filter(Visit::getIsEscaped).count();
        long failed = total - escaped;

        double escapeRate = total > 0
                ? (double) escaped / total * 100 : 0;

        double avgTime = visits.stream()
                .filter(v -> v.getEscapeTimeSec() != null)
                .mapToInt(Visit::getEscapeTimeSec)
                .average()
                .orElse(0);

        Map<String, Long> byGenre = visits.stream()
                .flatMap(v -> v.getEscapeRoom().getGenres().stream())
                .collect(Collectors.groupingBy(
                        g -> g.getName(),
                        Collectors.counting()
                ));

        Map<Integer, Long> byDifficulty = visits.stream()
                .collect(Collectors.groupingBy(
                        v -> (int) v.getEscapeRoom().getDifficulty(),
                        Collectors.counting()
                ));

        return new UserStatsResponse(
                total,
                escaped,
                failed,
                Math.round(escapeRate * 10.0) / 10.0,
                Math.round(avgTime * 10.0) / 10.0,
                byGenre,
                byDifficulty
        );
    }

    public RoomStatsResponse getRoomStats(UUID roomId) {
        if (!escapeRoomRepository.existsById(roomId)) {
            throw new NotFoundException("Escape room not found");
        }

        var visits = visitRepository.findByEscapeRoomId(roomId);

        long total = visits.size();
        long escaped = visits.stream().filter(Visit::getIsEscaped).count();

        double escapeRate = total > 0
                ? (double) escaped / total * 100 : 0;

        double avgTime = visits.stream()
                .filter(v -> v.getEscapeTimeSec() != null)
                .mapToInt(Visit::getEscapeTimeSec)
                .average()
                .orElse(0);

        return new RoomStatsResponse(
                total,
                escaped,
                Math.round(escapeRate * 10.0) / 10.0,
                Math.round(avgTime * 10.0) / 10.0
        );
    }
}