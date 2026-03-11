package com.github.arkadiuszszczesny.escaperoomtracker.domain.voivodeship;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VoivodeshipRepository extends JpaRepository<Voivodeship, UUID> {
    Optional<Voivodeship> findByName(String name);
}
