package com.github.arkadiuszszczesny.escaperoomtracker.domain.voivodeship;


import com.github.arkadiuszszczesny.escaperoomtracker.domain.voivodeship.dto.VoivodeshipResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/voivodeships")
@RequiredArgsConstructor
public class VoivodeshipController {

    private final VoivodeshipService voivodeshipService;

    @GetMapping
    public List<VoivodeshipResponse> getAll() {
        return voivodeshipService.getAll();
    }

    @GetMapping("/{id}")
    public VoivodeshipResponse getById(@PathVariable UUID id) {
        return voivodeshipService.getById(id);
    }
}