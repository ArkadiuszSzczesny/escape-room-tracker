package com.github.arkadiuszszczesny.escaperoomtracker.domain.visit;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.room.EscapeRoom;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "visits")
@Data
@NoArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "escape_room_id", nullable = false)
    private EscapeRoom escapeRoom;

    @Column(nullable = false)
    private LocalDate playedAt;

    @Column(nullable = false)
    private Boolean isEscaped;

    private Integer escapeTimeSec;

    @OneToMany(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VisitParticipant> participants = new ArrayList<>();
}