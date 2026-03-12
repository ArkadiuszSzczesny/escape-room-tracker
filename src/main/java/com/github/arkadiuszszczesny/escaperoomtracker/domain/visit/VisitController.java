package com.github.arkadiuszszczesny.escaperoomtracker.domain.visit;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.visit.dto.CreateVisitRequest;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.visit.dto.VisitResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    // Tymczasowo userId jako header — do zastapienia JWT
    @GetMapping("/my")
    public Page<VisitResponse> getMyVisits(
            @RequestHeader("X-User-Id") UUID userId,
            Pageable pageable) {
        return visitService.getMyVisits(userId, pageable);
    }

    @GetMapping("/{id}")
    public VisitResponse getById(@PathVariable UUID id) {
        return visitService.getById(id);
    }

    @GetMapping("/by-room/{escapeRoomId}")
    public Page<VisitResponse> getByRoom(
            @PathVariable UUID escapeRoomId,
            Pageable pageable) {
        return visitService.getByRoom(escapeRoomId, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VisitResponse create(
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody @Valid CreateVisitRequest request) {
        return visitService.create(userId, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id,
            @RequestHeader("X-User-Id") UUID userId) {
        visitService.delete(id, userId);
    }
}
