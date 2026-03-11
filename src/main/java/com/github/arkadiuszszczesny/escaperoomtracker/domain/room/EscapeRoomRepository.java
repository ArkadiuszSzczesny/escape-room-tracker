package com.github.arkadiuszszczesny.escaperoomtracker.domain.room;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface EscapeRoomRepository extends JpaRepository<EscapeRoom, UUID> {
    Page<EscapeRoom> findByBranch_City_Id(UUID cityId, Pageable pageable);
    Page<EscapeRoom> findByBranch_City_Voivodeship_Id(UUID voivodeshipId, Pageable pageable);
    Page<EscapeRoom> findByDifficulty(Short difficulty, Pageable pageable);
    Page<EscapeRoom> findByGenres_Name(String genreName, Pageable pageable);
}
