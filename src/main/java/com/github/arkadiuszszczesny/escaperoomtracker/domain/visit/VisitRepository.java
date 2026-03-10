package com.github.arkadiuszszczesny.escaperoomtracker.domain.visit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface VisitRepository extends JpaRepository<Visit, UUID> {
    Page<Visit> findByUserId(UUID userId, Pageable pageable);
    Page<Visit> findByEscapeRoomId(UUID escapeRoomId, Pageable pageable);
    boolean existsByUserIdAndEscapeRoomId(UUID userId, UUID escapeRoomId);
}