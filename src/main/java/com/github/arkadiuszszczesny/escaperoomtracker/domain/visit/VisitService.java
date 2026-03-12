package com.github.arkadiuszszczesny.escaperoomtracker.domain.visit;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.room.EscapeRoom;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.room.EscapeRoomRepository;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.user.User;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.user.UserRepository;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.visit.dto.CreateParticipantRequest;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.visit.dto.CreateVisitRequest;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.visit.dto.ParticipantResponse;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.visit.dto.VisitResponse;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.exception.BusinessException;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VisitService {

    private final VisitRepository visitRepository;
    private final EscapeRoomRepository escapeRoomRepository;
    private final UserRepository userRepository;

    public Page<VisitResponse> getMyVisits(UUID userId, Pageable pageable) {
        return visitRepository.findByUserId(userId, pageable)
                .map(this::toResponse);
    }

    public Page<VisitResponse> getByRoom(UUID escapeRoomId, Pageable pageable) {
        return visitRepository.findByEscapeRoomId(escapeRoomId, pageable)
                .map(this::toResponse);
    }

    public VisitResponse getById(UUID id) {
        return toResponse(visitRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Visit not found")));
    }

    @Transactional
    public VisitResponse create(UUID userId, CreateVisitRequest request) {

        EscapeRoom escapeRoom = escapeRoomRepository.findById(request.escapeRoomId())
                .orElseThrow(() -> new NotFoundException("Escape room not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!request.isEscaped() && request.escapeTimeSec() != null) {
            throw new BusinessException(
                    "Escape time can only be provided when isEscaped is true"
            );
        }

        Visit visit = new Visit();
        visit.setUser(user);
        visit.setEscapeRoom(escapeRoom);
        visit.setPlayedAt(request.playedAt());
        visit.setIsEscaped(request.isEscaped());
        visit.setEscapeTimeSec(request.escapeTimeSec());

        if (request.participants() != null) {
            List<VisitParticipant> participants = buildParticipants(
                    request.participants(), visit
            );
            visit.setParticipants(participants);
        }

        return toResponse(visitRepository.save(visit));
    }

    @Transactional
    public void delete(UUID id, UUID userId) {
        Visit visit = visitRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Visit not found"));

        if (!visit.getUser().getId().equals(userId)) {
            throw new BusinessException("You can only delete your own visits");
        }

        visitRepository.deleteById(id);
    }

    private List<VisitParticipant> buildParticipants(
            List<CreateParticipantRequest> requests, Visit visit) {

        List<VisitParticipant> participants = new ArrayList<>();

        for (CreateParticipantRequest req : requests) {

            if (req.companionUserId() == null && req.unregisteredName() == null) {
                throw new BusinessException(
                        "Participant must have either companionUserId or unregisteredName"
                );
            }
            if (req.companionUserId() != null && req.unregisteredName() != null) {
                throw new BusinessException(
                        "Participant cannot have both companionUserId and unregisteredName"
                );
            }

            VisitParticipant participant = new VisitParticipant();
            participant.setVisit(visit);

            if (req.companionUserId() != null) {
                User companion = userRepository.findById(req.companionUserId())
                        .orElseThrow(() -> new NotFoundException(
                                "Companion user not found: " + req.companionUserId()
                        ));
                participant.setCompanionUser(companion);
            } else {
                participant.setUnregisteredName(req.unregisteredName());
            }

            participants.add(participant);
        }

        return participants;
    }

    private VisitResponse toResponse(Visit visit) {
        List<ParticipantResponse> participants = visit.getParticipants()
                .stream()
                .map(p -> {
                    if (p.getCompanionUser() != null) {
                        return new ParticipantResponse(
                                p.getId(),
                                p.getCompanionUser().getUsername(),
                                true
                        );
                    } else {
                        return new ParticipantResponse(
                                p.getId(),
                                p.getUnregisteredName(),
                                false
                        );
                    }
                })
                .toList();

        return new VisitResponse(
                visit.getId(),
                visit.getEscapeRoom().getName(),
                visit.getEscapeRoom().getBranch().getCompany().getName(),
                visit.getEscapeRoom().getBranch().getCity().getName(),
                visit.getPlayedAt(),
                visit.getIsEscaped(),
                visit.getEscapeTimeSec(),
                participants
        );
    }
}
