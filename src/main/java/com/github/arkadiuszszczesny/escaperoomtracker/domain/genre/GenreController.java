package com.github.arkadiuszszczesny.escaperoomtracker.domain.genre;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.genre.dto.GenreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping
    public List<GenreResponse> getAll() {
        return genreService.getAll();
    }

    @GetMapping("/{id}")
    public GenreResponse getById(@PathVariable UUID id) {
        return genreService.getById(id);
    }
}
