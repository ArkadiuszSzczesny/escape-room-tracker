package com.github.arkadiuszszczesny.escaperoomtracker.domain.genre;

import com.github.arkadiuszszczesny.escaperoomtracker.infrastructure.persistence.BaseUUIDEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Entity
@Table(name = "genres")
@Data
@NoArgsConstructor
public class Genre extends BaseUUIDEntity {

    @Column(nullable = false, unique = true)
    private String name;
}
