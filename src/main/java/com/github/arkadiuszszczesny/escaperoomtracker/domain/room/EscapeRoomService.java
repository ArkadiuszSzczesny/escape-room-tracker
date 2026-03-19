package com.github.arkadiuszszczesny.escaperoomtracker.domain.room;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.branch.Branch;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.branch.BranchRepository;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.genre.Genre;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.genre.GenreRepository;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.genre.dto.GenreResponse;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.room.dto.CreateEscapeRoomRequest;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.room.dto.EscapeRoomResponse;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EscapeRoomService {

    private final EscapeRoomRepository escapeRoomRepository;
    private final BranchRepository branchRepository;
    private final GenreRepository genreRepository;

    public Page<EscapeRoomResponse> getAll(Pageable pageable) {
        return escapeRoomRepository.findAll(pageable)
                .map(this::toResponse);
    }

    public Page<EscapeRoomResponse> getByCity(UUID cityId, Pageable pageable) {
        return escapeRoomRepository.findByBranch_City_Id(cityId, pageable)
                .map(this::toResponse);
    }

    public Page<EscapeRoomResponse> getByVoivodeship(UUID voivodeshipId, Pageable pageable) {
        return escapeRoomRepository.findByBranch_City_Voivodeship_Id(voivodeshipId, pageable)
                .map(this::toResponse);
    }

    public Page<EscapeRoomResponse> getByDifficulty(Short difficulty, Pageable pageable) {
        return escapeRoomRepository.findByDifficulty(difficulty, pageable)
                .map(this::toResponse);
    }

    public Page<EscapeRoomResponse> getByGenre(String genreName,Pageable pageable) {
        return escapeRoomRepository.findByGenres_Name(genreName, pageable)
                .map(this::toResponse);
    }

    public EscapeRoomResponse getById(UUID id) {
        return toResponse(escapeRoomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Escape room not found")));
    }

    @Transactional
    public EscapeRoomResponse create(CreateEscapeRoomRequest request) {



        Branch branch = branchRepository.findById(request.branchId())
                .orElseThrow(() -> new NotFoundException("Branch not found"));

        Set<Genre> genres = new HashSet<>();
        if (request.genreIds() != null) {
            for (UUID genreId : request.genreIds()) {
                Genre genre = genreRepository.findById(genreId)
                        .orElseThrow(() -> new NotFoundException("Genre not found: " + genreId));
                genres.add(genre);
            }
        }

        EscapeRoom room = new EscapeRoom();
        room.setName(request.name());
        room.setDescription(request.description());
        room.setDifficulty(request.difficulty());
        room.setDurationMinutes(request.durationMinutes());
        room.setBranch(branch);
        room.setGenres(genres);

        return toResponse(escapeRoomRepository.save(room));
    }

    @Transactional
    public void delete(UUID id) {
        if (!escapeRoomRepository.existsById(id)) {
            throw new NotFoundException("Escape room not found");
        }
        escapeRoomRepository.deleteById(id);
    }

    private EscapeRoomResponse toResponse(EscapeRoom room) {
        Set<GenreResponse> genres = room.getGenres()
                .stream()
                .map(g -> new GenreResponse(g.getId(), g.getName()))
                .collect(Collectors.toSet());

        return new EscapeRoomResponse(
                room.getId(),
                room.getName(),
                room.getDescription(),
                room.getDifficulty(),
                room.getDurationMinutes(),
                room.getBranch().getAddress(),
                room.getBranch().getCompany().getName(),
                room.getBranch().getCity().getName(),
                room.getBranch().getCity().getVoivodeship().getName(),
                genres
        );
    }
}
