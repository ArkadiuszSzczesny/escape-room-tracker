package com.github.arkadiuszszczesny.escaperoomtracker.domain.genre;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.genre.dto.GenreResponse;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GenreService {

    private final GenreRepository genreRepository;

    public List<GenreResponse> getAll() {
        return genreRepository.findAll()
                .stream()
                .map(g -> new GenreResponse(g.getId(), g.getName()))
                .toList();
    }

    public GenreResponse getById(UUID id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Genre not found"));
        return new GenreResponse(genre.getId(), genre.getName());
    }
}
