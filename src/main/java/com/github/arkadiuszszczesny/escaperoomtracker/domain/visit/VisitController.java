package com.github.arkadiuszszczesny.escaperoomtracker.domain.visit;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.visit.dto.CreateVisitRequest;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.visit.dto.VisitResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @GetMapping("/my")
    public Page<VisitResponse> getMyVisits(
            Authentication authentication,
            Pageable pageable) {
        UUID userId = (UUID) authentication.getPrincipal();
        return visitService.getMyVisits(userId, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VisitResponse create(
            Authentication authentication,
            @RequestBody @Valid CreateVisitRequest request) {
        UUID userId = (UUID) authentication.getPrincipal();
        return visitService.create(userId, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id,
            Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();
        visitService.delete(id, userId);
    }
}
