package com.github.arkadiuszszczesny.escaperoomtracker.domain.visit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface VisitParticipantRepository extends JpaRepository<VisitParticipant, UUID> {
    List<VisitParticipant> findByVisitId(UUID visitId);
    List<VisitParticipant> findByCompanionUserId(UUID userId);
}
