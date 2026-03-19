package com.github.arkadiuszszczesny.escaperoomtracker.domain.room;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.room.dto.CreateEscapeRoomRequest;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.room.dto.EscapeRoomResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class EscapeRoomController {

    private final EscapeRoomService escapeRoomService;

    @GetMapping
    public Page<EscapeRoomResponse> getAll(Pageable pageable) {
        return escapeRoomService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public EscapeRoomResponse getById(@PathVariable UUID id) {
        return escapeRoomService.getById(id);
    }

    @GetMapping("/by-city/{cityId}")
    public Page<EscapeRoomResponse> getByCity(@PathVariable UUID cityId, Pageable pageable) {
        return escapeRoomService.getByCity(cityId, pageable);
    }

    @GetMapping("/by-voivodeship/{voivodeshipId}")
    public Page<EscapeRoomResponse> getByVoivodeship(@PathVariable UUID voivodeshipId, Pageable pageable) {
        return escapeRoomService.getByVoivodeship(voivodeshipId, pageable);
    }

    @GetMapping("/by-difficulty/{difficulty}")
    public Page<EscapeRoomResponse> getByDifficulty(@PathVariable Short difficulty, Pageable pageable) {
        return escapeRoomService.getByDifficulty(difficulty, pageable);
    }

    @GetMapping("/by-genre/{genreName}")
    public Page<EscapeRoomResponse> getByGenre(@PathVariable String genreName, Pageable pageable) {
        return escapeRoomService.getByGenre(genreName, pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EscapeRoomResponse create(@RequestBody @Valid CreateEscapeRoomRequest request) {
        return escapeRoomService.create(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        escapeRoomService.delete(id);
    }
}
