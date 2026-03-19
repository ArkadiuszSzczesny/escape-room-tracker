package com.github.arkadiuszszczesny.escaperoomtracker.domain.visit;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "visit_participants")
@Data
@NoArgsConstructor
public class VisitParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", nullable = false)
    private Visit visit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "companion_user_id")
    private User companionUser;

    @Column(name = "unregistered_name")
    private String unregisteredName;
}
