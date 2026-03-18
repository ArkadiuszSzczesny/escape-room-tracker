package com.github.arkadiuszszczesny.escaperoomtracker.domain.visit;


import com.github.arkadiuszszczesny.escaperoomtracker.domain.branch.Branch;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.city.City;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.company.Company;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.room.EscapeRoom;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.room.EscapeRoomRepository;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.user.User;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.user.UserRepository;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.visit.dto.CreateVisitRequest;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.voivodeship.Voivodeship;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.exception.BusinessException;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitServiceTest {

    @Mock
    private VisitRepository visitRepository;

    @Mock
    private EscapeRoomRepository escapeRoomRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private VisitService visitService;

    private User testUser;
    private EscapeRoom testRoom;
    private UUID userId;
    private UUID roomId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        roomId = UUID.randomUUID();

        testUser = new User();
        testUser.setId(userId);
        testUser.setUsername("testuser");
        testUser.setEmail("test@test.com");

        Voivodeship voivodeship = new Voivodeship();
        voivodeship.setName("Mazowieckie");

        City city = new City();
        city.setName("Warszawa");
        city.setVoivodeship(voivodeship);

        Company company = new Company();
        company.setName("Lockme");

        Branch branch = new Branch();
        branch.setAddress("ul. Marszałkowska 1");
        branch.setCompany(company);
        branch.setCity(city);

        testRoom = new EscapeRoom();
        testRoom.setId(roomId);
        testRoom.setName("Test Room");
        testRoom.setDifficulty((short) 3);
        testRoom.setDurationMinutes(60);
        testRoom.setGenres(new HashSet<>());
        testRoom.setBranch(branch);

    }

    @Test
    void create_shouldCreateVisit_whenDataIsValid() {

        CreateVisitRequest request = new CreateVisitRequest(
                roomId,
                LocalDate.now().minusDays(1),
                true,
                2700,
                new ArrayList<>()
        );

        Visit savedVisit = new Visit();
        savedVisit.setId(UUID.randomUUID());
        savedVisit.setUser(testUser);
        savedVisit.setEscapeRoom(testRoom);
        savedVisit.setPlayedAt(request.playedAt());
        savedVisit.setIsEscaped(true);
        savedVisit.setEscapeTimeSec(2700);
        savedVisit.setParticipants(new ArrayList<>());

        when(escapeRoomRepository.findById(roomId)).thenReturn(Optional.of(testRoom));
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));
        when(visitRepository.save(any(Visit.class))).thenReturn(savedVisit);

        var response = visitService.create(userId, request);

        assertThat(response).isNotNull();
        assertThat(response.isEscaped()).isTrue();
        assertThat(response.escapeTimeSec()).isEqualTo(2700);
        verify(visitRepository, times(1)).save(any(Visit.class));
    }

    @Test
    void create_shouldThrowBusinessException_whenEscapeTimeProvidedButNotEscaped() {

        CreateVisitRequest request = new CreateVisitRequest(
                roomId,
                LocalDate.now().minusDays(1),
                false,
                2700,
                new ArrayList<>()
        );

        when(escapeRoomRepository.findById(roomId)).thenReturn(Optional.of(testRoom));
        when(userRepository.findById(userId)).thenReturn(Optional.of(testUser));

        assertThatThrownBy(() -> visitService.create(userId, request))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("Escape time can only be provided when isEscaped is true");

        verify(visitRepository, never()).save(any());
    }

    @Test
    void create_shouldThrowNotFoundException_whenRoomNotFound() {

        CreateVisitRequest request = new CreateVisitRequest(
                roomId,
                LocalDate.now().minusDays(1),
                true,
                null,
                new ArrayList<>()
        );

        when(escapeRoomRepository.findById(roomId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> visitService.create(userId, request))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Escape room not found");

        verify(visitRepository, never()).save(any());
    }

    @Test
    void delete_shouldThrowBusinessException_whenUserIsNotOwner() {

        UUID otherUserId = UUID.randomUUID();
        UUID visitId = UUID.randomUUID();

        User otherUser = new User();
        otherUser.setId(otherUserId);

        Visit visit = new Visit();
        visit.setId(visitId);
        visit.setUser(otherUser);

        when(visitRepository.findById(visitId)).thenReturn(Optional.of(visit));

        assertThatThrownBy(() -> visitService.delete(visitId, userId))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("You can only delete your own visits");
    }
}