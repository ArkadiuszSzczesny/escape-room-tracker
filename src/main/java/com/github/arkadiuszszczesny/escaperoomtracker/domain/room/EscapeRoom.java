package com.github.arkadiuszszczesny.escaperoomtracker.domain.room;

import com.github.arkadiuszszczesny.escaperoomtracker.domain.branch.Branch;
import com.github.arkadiuszszczesny.escaperoomtracker.domain.genre.Genre;
import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.persistence.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "escape_rooms")
@Data
@NoArgsConstructor
public class EscapeRoom extends BaseUUIDEntity {

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Short difficulty;

    @Column(nullable = false)
    private Integer durationMinutes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id", nullable = false)
    private Branch branch;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "escape_room_genres",
            joinColumns = @JoinColumn(name = "escape_room_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();
}
